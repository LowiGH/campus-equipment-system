package edu.cit.bajamunde.louie.campusequipmentloan.controller;

import edu.cit.bajamunde.louie.campusequipmentloan.model.Loan;

public class ReturnResponse {
    private Loan loan;
    private long penalty;

    public ReturnResponse() {}
    public ReturnResponse(Loan loan, long penalty) { this.loan = loan; this.penalty = penalty; }

    public Loan getLoan() { return loan; }
    public void setLoan(Loan loan) { this.loan = loan; }

    public long getPenalty() { return penalty; }
    public void setPenalty(long penalty) { this.penalty = penalty; }
}
