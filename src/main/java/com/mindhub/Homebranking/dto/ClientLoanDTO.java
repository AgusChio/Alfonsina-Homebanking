package com.mindhub.Homebranking.dto;

import com.mindhub.Homebranking.models.ClientLoan;
import java.time.LocalDateTime;

public class ClientLoanDTO {
    private long id;
    private long loanId;
    private String name;
    private double amount;
    private int payments;
    private LocalDateTime creationDate;

    public ClientLoanDTO(ClientLoan clientLoan){
        id = clientLoan.getId();
        loanId = clientLoan.getLoan().getId();
        name = clientLoan.getLoan().getName();
        amount = clientLoan.getAmount();
        payments = clientLoan.getPayments();
        creationDate= clientLoan.getCreationDate();
    }

    public long getId() {
        return id;
    }
    public long getLoanId() {
        return loanId;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }
    public int getPayments() {
        return payments;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
