package edu.cit.bajamunde.louie.campusequipmentloan.controller;

import edu.cit.bajamunde.louie.campusequipmentloan.model.Loan;
import edu.cit.bajamunde.louie.campusequipmentloan.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;
    public LoanController(LoanService loanService) { this.loanService = loanService; }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody LoanRequest request) {
        LocalDate start = request.getStartDate() != null ? request.getStartDate() : LocalDate.now();
        Loan created = loanService.createLoan(request.getEquipmentId(), request.getStudentId(), start);
        return ResponseEntity.created(URI.create("/api/loans/" + created.getId())).body(created);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<ReturnResponse> returnLoan(@PathVariable Long id,
                                                     @RequestBody(required = false) ReturnResponse body) {
        LocalDate returnDate = (body != null && body.getLoan() != null && body.getLoan().getReturnDate() != null)
                ? body.getLoan().getReturnDate()
                : LocalDate.now();

        LoanService.ReturnResult res = loanService.returnLoan(id, returnDate);
        ReturnResponse out = new ReturnResponse(res.getLoan(), res.getPenalty());
        return ResponseEntity.ok(out);
    }
}
