package com.mindhub.Homebranking.controllers;

import com.mindhub.Homebranking.dto.AccountDTO;
import com.mindhub.Homebranking.models.*;
import com.mindhub.Homebranking.services.AccountService;
import com.mindhub.Homebranking.services.CardService;
import com.mindhub.Homebranking.services.ClientService;
import com.mindhub.Homebranking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class AccountController {


    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CardService cardService;



    @RequestMapping("/accounts")
    public List<AccountDTO> getAccount(){
        return accountService.accountToAccountsDTO(accountService.findAllAccounts());
    };

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountService.accountToAccountsDTO(accountService.getAccountsById(id));
    };

    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication, @RequestParam AccountType accountType){
        Client client = clientService.findByEmail(authentication.getName());
        if (client.getAccounts().stream().filter(account -> account.isActive_account()).collect(Collectors.toSet()).size() == 3){
            return new ResponseEntity<>("403 forbidden", HttpStatus.FORBIDDEN);
        };
        Account account = new Account( "VIN-" + (int)(Math.random()*(99999999 - 10000000) + 10000000), LocalDateTime.now(), 0, client,true, accountType);
        accountService.saveAccount(account);
        return new ResponseEntity<>("201 created", HttpStatus.CREATED);
    }

    @PatchMapping("/clients/current/accounts/{id}")
    public ResponseEntity<Object> deleteAccount(Authentication authentication, @PathVariable long id){
        Client client = clientService.findByEmail(authentication.getName());
        Set<Account> accounts = client.getAccounts();
        Account accountClient = accountService.getAccountsById(id);
        Account accountFound = accounts.stream().filter(account -> account == accountClient).findFirst().orElse(null);
        Set<Card> accountCards = accountFound.getCards().stream().filter(card -> card.isActive_card() == true).collect(Collectors.toSet());

        if (accountClient.getBalance() > 0 ){
            return new ResponseEntity<>("You can't delete a account with balance",HttpStatus.FORBIDDEN);
        }

        if(accountCards.size() > 0) {
            return new ResponseEntity<>("You can't delete a account with cards", HttpStatus.FORBIDDEN);
        }


        accountService.getAccountsById(accountFound.getId()).setActive_account(false);
        List<Transaction> transactionsFounds = accountFound.getTransactions().stream().map(transaction -> {
            transaction.setActive_transaction(false);
            return transaction;
        }).collect(Collectors.toList());
        transactionService.saveAllTransaction(transactionsFounds);
        accountService.saveAccount(accountFound);


        return new ResponseEntity<>("Account Delete", HttpStatus.OK);
    }

}


