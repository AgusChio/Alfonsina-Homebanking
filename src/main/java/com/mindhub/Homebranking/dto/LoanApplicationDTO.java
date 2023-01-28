package com.mindhub.Homebranking.dto;

public class LoanApplicationDTO {

    private long idLoans;

    private double amount;

    private int payments;

    private String accountDestiny;

    public LoanApplicationDTO(String accountDestiny,long idloans ,double amount, int payments){
        this.accountDestiny = accountDestiny;
        this.idLoans = idloans;
        this.amount = amount;
        this.payments = payments;
    }

    public long getIdLoans() {
        return idLoans;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getAccountDestiny() {
        return accountDestiny;
    }
}
