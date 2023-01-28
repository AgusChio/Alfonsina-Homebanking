package com.mindhub.Homebranking.services;

import com.mindhub.Homebranking.models.Card;

public interface CardService {

    void saveCard(Card card);

    Card getCardById(long id);

    Card getCardByNumber(String number);
}
