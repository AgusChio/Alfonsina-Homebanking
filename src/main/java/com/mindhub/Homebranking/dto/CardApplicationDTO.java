package com.mindhub.Homebranking.dto;

import com.mindhub.Homebranking.models.CardType;

import java.time.LocalDate;

public class CardApplicationDTO {

    private String cardHolder;

    private String number;

    private  int cvv;

    private LocalDate thruDate;

    private double amount;

    private String description;

    private CardType type;


    public CardApplicationDTO(String cardHolder, String number, int cvv, LocalDate thruDate, double amount, String description, CardType type) {
        this.cardHolder = cardHolder;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.amount = amount;
        this.description = description;
        this.type = type;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public CardType getType() {
        return type;
    }
}
