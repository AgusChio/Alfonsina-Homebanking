package com.mindhub.Homebranking.dto;

import com.mindhub.Homebranking.models.Card;
import com.mindhub.Homebranking.models.CardColor;
import com.mindhub.Homebranking.models.CardType;
import java.time.LocalDate;

public class CardDTO {

    private long id;
    private String cardholder;
    private String number;
    private int cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;
    private CardType type;
    private CardColor color;
    private boolean active_card;


    public CardDTO(Card card){
        id = card.getId();
        cardholder = card.getCardholder();
        number = card.getNumber();
        cvv = card.getCvv();
        fromDate = card.getFromDate();
        thruDate = card.getThruDate();
        type = card.getType();
        color = card.getColor();
        active_card = card.isActive_card();
    }

    public long getId() {
        return id;
    }

    public String getCardholder() {
        return cardholder;
    }

    public String getNumber() {
        return number;
    }

    public int getCvv() {
        return cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public CardType getType() {
        return type;
    }

    public CardColor getColor() {
        return color;
    }

    public boolean isActive_card() {
        return active_card;
    }
}
