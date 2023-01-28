package com.mindhub.Homebranking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ClientLoan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private double amount;
    private int payments;

    private LocalDateTime creationDate;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="loan_id")
    private Loan loan;

    public ClientLoan(){}

    public ClientLoan(double amount, int payments, LocalDateTime creationDate ,Client client, Loan loan) {
        this.amount = amount;
        this.payments = payments;
        this.client = client;
        this.loan = loan;
        this.creationDate = creationDate;
    }

    public long getId() {
        return id;
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

    public Client getClient() {
        return client;
    }
    public Loan getLoan() {
        return loan;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setPayments(int payments) {
        this.payments = payments;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public void setLoan(Loan loan) {
        this.loan = loan;
    }
}
