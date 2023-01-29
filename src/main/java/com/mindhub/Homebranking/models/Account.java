package com.mindhub.Homebranking.models;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id" )
    private Client client;

    @OneToMany(mappedBy="account",fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>(); //El HashSet me reserva un espacio de memoria para el set

    @OneToMany(mappedBy="account",fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    private boolean active_account;

    private AccountType accountType;

    public Account() { }

    public Account(String number, LocalDateTime creationDate, double balance, Client client, boolean active_account, AccountType accountType) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client = client;
        this.active_account = active_account;
        this.accountType = accountType;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Client getClient() {
        return client;
    }

    public Double getBalance() {
        return balance;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public boolean isActive_account() {
        return active_account;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void setActive_account(boolean active_account) {
        this.active_account = active_account;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void addTransaction (Transaction transaction){
        transaction.setAccount(this);
        transactions.add(transaction);
    }

    public void addCard (Card card){
        card.setAccount(this);
        cards.add(card);
    }
}
