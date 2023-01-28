package com.mindhub.Homebranking.services.impl;

import com.mindhub.Homebranking.models.Transaction;
import com.mindhub.Homebranking.repositories.TransactionRepository;
import com.mindhub.Homebranking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public void saveAllTransaction(List<Transaction> transactions) {
        transactionRepository.saveAll(transactions);
    }

    @Override
    public Set<Transaction> filterTransactionByDate(LocalDateTime dateStart, LocalDateTime dateEnd, Set<Transaction> transactionsAccount) {
        return transactionsAccount.stream().filter(transaction -> transaction.getTransactionDate().isAfter(dateStart) && transaction.getTransactionDate().isBefore(dateEnd)).collect(Collectors.toSet());
    }
}
