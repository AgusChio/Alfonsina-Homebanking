package com.mindhub.Homebranking;


import com.mindhub.Homebranking.models.*;
import com.mindhub.Homebranking.repositories.*;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {


    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    TransactionRepository transactionRepository;


    @Test
    public void validateSizeAllAccounts(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, Matchers.hasSize(4));
    }


    @Test
    public void existNumberAccount(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, hasItem((hasProperty("number", is("VIN001")))));
    }


    @Test
    public void existCardCVV(){
        List<Card> cards = cardRepository.findById(20l).stream().collect(Collectors.toList());
        assertThat(cards, hasItem((hasProperty("cvv",is(875)))));
    }


    @Test
    public void existColorCard(){
        List<Card> cards = cardRepository.findAll().stream().filter( card -> card.getColor() == CardColor.TITANIUM).collect(Collectors.toList());
        assertThat(cards,is(not(empty())));
    }


    @Test
    public void existClientFistName(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, hasItem(hasProperty("firstName",is("Melba"))));
    }

    @Test
    public void ExistAllClients(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }


    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }


    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));

    }


    @Test
    public void existTransactionDate(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,hasItem(hasProperty("transactionDate", is(not(empty())))));
    }

    @Test
    public void existTransactionType(){
        List<Transaction> transactions = transactionRepository.findAll().stream().filter(transaction -> transaction.getType() == TransactionType.DEBIT).collect(Collectors.toList());
        assertThat(transactions, is(not(empty())));
    }


}
