package com.mindhub.Homebranking.dto;

import com.mindhub.Homebranking.models.Transaction;
import com.mindhub.Homebranking.models.TransactionType;
import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private Double amount;
    private LocalDateTime transactionDate;
    private String description;
    private TransactionType type;
    private double balance;
    private boolean active_transaction;

    private double actualBalance;

    public TransactionDTO (Transaction transaction){
        id = transaction.getId();
        amount = transaction.getAmount();
        transactionDate = transaction.getTransactionDate();
        description = transaction.getDescription();
        type = transaction.getType();
        active_transaction = transaction.isActive_transaction();
        actualBalance = transaction.getActualBalance();
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public TransactionType getType() {
        return type;
    }

    public boolean isActive_transaction() {
        return active_transaction;
    }

    public double getActualBalance() {
        return actualBalance;
    }
}
