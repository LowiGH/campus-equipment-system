package edu.cit.bajamunde.louie.campusequipmentloan.controller;

import java.time.LocalDate;

public class LoanRequest {
    private Long equipmentId;
    private Long studentId;
    private LocalDate startDate;

    public Long getEquipmentId() {
        return equipmentId;
    }
    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getStudentId() {
        return studentId;
    }
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    // convenience: calculate due date (7 days after start)
    public LocalDate getDueDate() {
        LocalDate effectiveStart = (startDate != null) ? startDate : LocalDate.now();
        return effectiveStart.plusDays(7);
    }
}
