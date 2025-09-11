package edu.cit.bajamunde.louie.campusequipmentloan.controller;

import edu.cit.bajamunde.louie.campusequipmentloan.model.Loan;
import edu.cit.bajamunde.louie.campusequipmentloan.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    private final LoanService loanService;
    public LoanController(LoanService loanService) { this.loanService = loanService; }

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody Map<String, String> body) {
        Long equipmentId = Long.valueOf(body.get("equipmentId"));
        Long studentId = Long.valueOf(body.get("studentId"));
        LocalDate start = LocalDate.parse(body.get("startDate"));
        LocalDate due = LocalDate.parse(body.get("dueDate"));

        Loan loan = loanService.createLoan(equipmentId, studentId, start, due);
        return ResponseEntity.created(URI.create("/api/loans/" + loan.getId())).body(loan);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<?> returnLoan(@PathVariable Long id,
                                        @RequestBody(required = false) Map<String, String> body) {
        LocalDate returnDate = (body != null && body.get("returnDate") != null)
                ? LocalDate.parse(body.get("returnDate"))
                : LocalDate.now();

        Loan returned = loanService.returnLoan(id, returnDate);
        return ResponseEntity.ok(returned);
    }
}
