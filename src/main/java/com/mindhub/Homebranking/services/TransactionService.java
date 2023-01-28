package com.mindhub.Homebranking.services;

import com.mindhub.Homebranking.models.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface TransactionService {

    void  saveTransaction(Transaction transaction);

    void saveAllTransaction(List<Transaction> transactions);

    Set<Transaction> filterTransactionByDate(LocalDateTime dateStart, LocalDateTime dateEnd, Set<Transaction> transactionsAccount);
}
