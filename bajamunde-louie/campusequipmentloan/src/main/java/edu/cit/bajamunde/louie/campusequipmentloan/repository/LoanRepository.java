package edu.cit.bajamunde.louie.campusequipmentloan.repository;

import edu.cit.bajamunde.louie.campusequipmentloan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStudentIdAndStatusIn(Long studentId, List<Loan.Status> statuses);
}
