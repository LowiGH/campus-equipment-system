package edu.cit.bajamunde.louie.campusequipmentloan.penalty;

import java.time.LocalDate;

public interface LatePenaltyStrategy {

    long calculatePenalty(LocalDate dueDate, LocalDate returnDate);
}
