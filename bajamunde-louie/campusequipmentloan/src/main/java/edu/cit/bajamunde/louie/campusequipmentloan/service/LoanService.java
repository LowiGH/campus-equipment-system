package edu.cit.bajamunde.louie.campusequipmentloan.service;

import edu.cit.bajamunde.louie.campusequipmentloan.model.Equipment;
import edu.cit.bajamunde.louie.campusequipmentloan.model.Loan;
import edu.cit.bajamunde.louie.campusequipmentloan.model.Student;
import edu.cit.bajamunde.louie.campusequipmentloan.repository.EquipmentRepository;
import edu.cit.bajamunde.louie.campusequipmentloan.repository.LoanRepository;
import edu.cit.bajamunde.louie.campusequipmentloan.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class LoanService {
    private final LoanRepository loanRepo;
    private final EquipmentRepository equipmentRepo;
    private final StudentRepository studentRepo;

    public LoanService(LoanRepository loanRepo, EquipmentRepository equipmentRepo, StudentRepository studentRepo) {
        this.loanRepo = loanRepo;
        this.equipmentRepo = equipmentRepo;
        this.studentRepo = studentRepo;
    }

    @Transactional
    public Loan createLoan(Long equipmentId, Long studentId, LocalDate startDate, LocalDate dueDate) {
        Equipment eq = equipmentRepo.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));
        if (!eq.isAvailability()) {
            throw new IllegalStateException("Equipment is not available");
        }
        Student st = studentRepo.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        Loan loan = new Loan();
        loan.setEquipment(eq);
        loan.setStudent(st);
        loan.setStartDate(startDate);
        loan.setDueDate(dueDate);
        loan.setStatus(Loan.Status.ON_LOAN);

        eq.setAvailability(false);
        equipmentRepo.save(eq);

        return loanRepo.save(loan);
    }

    @Transactional
    public Loan returnLoan(Long loanId, LocalDate returnDate) {
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

        return loanRepo.save(loan);
    }
}
