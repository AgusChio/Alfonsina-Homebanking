package com.mindhub.Homebranking.dto;

import com.mindhub.Homebranking.models.Account;
import com.mindhub.Homebranking.models.AccountType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;

    private List<AccountDTO> accounts;

    private List<TransactionDTO> transactions;

    private  List<CardDTO> cards;

    private boolean active_account;

    private AccountType accountType;

    public AccountDTO(Account account) {
        id = account.getId();
        number = account.getNumber();
        creationDate = account.getCreationDate();
        balance = account.getBalance();
        transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toList());
        cards = account.getCards().stream().filter(card -> card.isActive_card()).map(card -> new CardDTO(card) ).collect(Collectors.toList());
        active_account = account.isActive_account();
        accountType = account.getAccountType();
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public boolean isActive_account() {
        return active_account;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}


