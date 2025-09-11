package edu.cit.bajamunde.louie.campusequipmentloan.repository;

import edu.cit.bajamunde.louie.campusequipmentloan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
