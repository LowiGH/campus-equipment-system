package edu.cit.bajamunde.louie.campusequipmentloan.service;

import edu.cit.bajamunde.louie.campusequipmentloan.model.Equipment;
import edu.cit.bajamunde.louie.campusequipmentloan.model.Loan;
import edu.cit.bajamunde.louie.campusequipmentloan.model.Student;
import edu.cit.bajamunde.louie.campusequipmentloan.penalty.FixedRateLatePenaltyStrategy;
import edu.cit.bajamunde.louie.campusequipmentloan.penalty.LatePenaltyStrategy;
import edu.cit.bajamunde.louie.campusequipmentloan.repository.EquipmentRepository;
import edu.cit.bajamunde.louie.campusequipmentloan.repository.LoanRepository;
import edu.cit.bajamunde.louie.campusequipmentloan.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    private final LoanRepository loanRepo;
    private final EquipmentRepository equipmentRepo;
    private final StudentRepository studentRepo;
    private final LatePenaltyStrategy penaltyStrategy = new FixedRateLatePenaltyStrategy(50);

    private static final int LOAN_LENGTH_DAYS = 7;
    private static final int MAX_ACTIVE_LOANS = 2;

    public LoanService(LoanRepository loanRepo,
                       EquipmentRepository equipmentRepo,
                       StudentRepository studentRepo) {
        this.loanRepo = loanRepo;
        this.equipmentRepo = equipmentRepo;
        this.studentRepo = studentRepo;
    }

    @Transactional
    public Loan createLoan(Long equipmentId, Long studentId, LocalDate startDate) {
        if (startDate == null) startDate = LocalDate.now();

        Equipment equipment = equipmentRepo.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));

        if (!equipment.isAvailability()) {
            throw new IllegalStateException("Equipment is not available");
        }

        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        List<Loan> active = loanRepo.findByStudentIdAndStatusIn(student.getId(),
                List.of(Loan.Status.ON_LOAN, Loan.Status.LATE));
        if (active.size() >= MAX_ACTIVE_LOANS) {
            throw new IllegalStateException("Student has reached max active loans: " + MAX_ACTIVE_LOANS);
        }

        Loan loan = new Loan();
        loan.setEquipment(equipment);
        loan.setStudent(student);
        loan.setStartDate(startDate);
        loan.setDueDate(startDate.plusDays(LOAN_LENGTH_DAYS));
        loan.setStatus(Loan.Status.ON_LOAN);

        equipment.setAvailability(false);
        equipmentRepo.save(equipment);

        return loanRepo.save(loan);
    }

    @Transactional
    public ReturnResult returnLoan(Long loanId, LocalDate returnDate) {
        if (returnDate == null) returnDate = LocalDate.now();

        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));

        if (loan.getStatus() == Loan.Status.RETURNED) {
            throw new IllegalStateException("Loan already returned");
        }

        loan.setReturnDate(returnDate);
        if (loan.getDueDate() != null && returnDate.isAfter(loan.getDueDate())) {
            loan.setStatus(Loan.Status.LATE);
        } else {
            loan.setStatus(Loan.Status.RETURNED);
        }

        Equipment eq = loan.getEquipment();
        eq.setAvailability(true);
        equipmentRepo.save(eq);

        Loan saved = loanRepo.save(loan);
        long penalty = penaltyStrategy.calculatePenalty(saved.getDueDate(), saved.getReturnDate());
        return new ReturnResult(saved, penalty);
    }

    public static class ReturnResult {
        private final Loan loan;
        private final long penalty;
        public ReturnResult(Loan loan, long penalty) { this.loan = loan; this.penalty = penalty; }
        public Loan getLoan() { return loan; }
        public long getPenalty() { return penalty; }
    }
}
