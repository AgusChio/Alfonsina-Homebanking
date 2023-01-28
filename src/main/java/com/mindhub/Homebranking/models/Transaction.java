package com.mindhub.Homebranking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private double amount;
    private String description;
    private LocalDateTime transactionDate;

    private TransactionType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    private boolean active_transaction;

    private double actualBalance;

    public Transaction() {}

    public Transaction(double amount, TransactionType type, LocalDateTime transactionDate, String description, Account account, boolean active_transaction) {
        this.amount = amount;
        this.type = type;
        this.transactionDate = transactionDate;
        this.description = description;
        this.account = account;
        this.active_transaction = active_transaction;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public Account getAccount() {
        return account;
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

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setActive_transaction(boolean active_transaction) {
        this.active_transaction = active_transaction;
    }

    public void setActualBalance(double actualBalance) {
        this.actualBalance = actualBalance;
    }
}

