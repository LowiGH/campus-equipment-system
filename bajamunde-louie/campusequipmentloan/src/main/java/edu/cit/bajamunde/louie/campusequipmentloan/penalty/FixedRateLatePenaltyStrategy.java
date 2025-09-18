package edu.cit.bajamunde.louie.campusequipmentloan.penalty;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FixedRateLatePenaltyStrategy implements LatePenaltyStrategy {
    private final long perDay;

    public FixedRateLatePenaltyStrategy(long perDay) { this.perDay = perDay; }

    @Override
    public long calculatePenalty(LocalDate dueDate, LocalDate returnDate) {
        if (dueDate == null || returnDate == null) return 0;
        if (!returnDate.isAfter(dueDate)) return 0;
        long daysLate = ChronoUnit.DAYS.between(dueDate, returnDate);
        return Math.max(0, daysLate * perDay);
    }
}
