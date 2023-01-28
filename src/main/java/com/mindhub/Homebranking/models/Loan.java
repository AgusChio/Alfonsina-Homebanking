package com.mindhub.Homebranking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.*;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ElementCollection
    @Column(name="payment")
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();
    private String name;
    private int maxAmount;

    private double interest_percentaje;

    public Loan(){}

    public Loan(String name, int maxAmount, List<Integer> payments, double interest_percentaje){
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.interest_percentaje = interest_percentaje;
    };

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayment() {
        return payments;
    }

    public double getInterest_percentaje() {
        return interest_percentaje;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaxAmount(int maxAmount) {
        this.maxAmount = maxAmount;
    }

    public void setPayment(List<Integer> payment) {
        this.payments = payment;
    }

    public void setInterest_percentaje(double interest_percentaje) {
        this.interest_percentaje = interest_percentaje;
    }
}
