package com.mindhub.Homebranking.dto;

import com.mindhub.Homebranking.models.Loan;
import java.util.List;

public class LoanDTO {
    private long id;
    private String name;
    private int maxAmount;
    private List<Integer> payments;

    private double interest_percentaje;

    public LoanDTO() {
    }

    public LoanDTO(Loan loan){
        id = loan.getId();
        name = loan.getName();
        maxAmount = loan.getMaxAmount();
        payments = loan.getPayment();
        interest_percentaje = loan.getInterest_percentaje();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public double getInterest_percentaje() {
        return interest_percentaje;
    }
}
