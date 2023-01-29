package com.mindhub.Homebranking.controllers;

import com.mindhub.Homebranking.dto.LoanApplicationDTO;
import com.mindhub.Homebranking.dto.LoanDTO;
import com.mindhub.Homebranking.models.*;
import com.mindhub.Homebranking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanService loanService;

    @Autowired
    ClientService clientService;

    @Autowired
    AccountService accountService;

    @Autowired
    ClientLoanService clientLoanService;

    @Autowired
    TransactionService transactionService;


    @RequestMapping("/loans")
    public List<LoanDTO> getLoans() {
        return loanService.loanToLoanDTO(loanService.findAllLoans());
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> createLoans(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        Client client = clientService.findByEmail(authentication.getName());
        Loan loanExist = loanService.getLoansById(loanApplicationDTO.getIdLoans());
        Account accountDestiny = accountService.findByNumberAccount(loanApplicationDTO.getAccountDestiny());

        if (loanApplicationDTO.getAmount() < 0){
            return new ResponseEntity<>("Incorrect Amount", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() == 0){
            return new ResponseEntity<>("Missing Amount", HttpStatus.FORBIDDEN);
        }

        if(!loanExist.getPayment().contains(loanApplicationDTO.getPayments())) {
            return new ResponseEntity<>("The number of installments is not among those available for the loan",HttpStatus.FORBIDDEN);
        }

        if (loanExist == null) {
            return new ResponseEntity<>("Loan Doesn't Exist",HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() > loanExist.getMaxAmount()){
            return new ResponseEntity<>("The amount requested is greater than the amount of the loan",HttpStatus.FORBIDDEN);
        }

        if (!loanExist.getPayment().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("Wrong  amount of payments",HttpStatus.FORBIDDEN);
        }

        if (accountDestiny == null){
            return new ResponseEntity<>("Your Destiny Account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (!client.getAccounts().contains(accountDestiny)){
            return new ResponseEntity<>("Destiny Account doesn't belong to any authenticated client", HttpStatus.FORBIDDEN);
        }

        if (client.getClientLoans().stream().filter(clientLoan -> clientLoan.getLoan().getId() == loanExist.getId()).count() == 1) {
            return new ResponseEntity<>("You can't have two loans of the same type", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount() < 50000){
            return new ResponseEntity<>("The amount must be greater than $500000", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() * loanExist.getInterest_percentaje(), loanApplicationDTO.getPayments(), LocalDateTime.now(), client, loanExist);
        clientLoanService.saveClientLoans(clientLoan);

        Transaction transaction = new Transaction(loanApplicationDTO.getAmount(), TransactionType.CREDIT, LocalDateTime.now(), loanExist.getName() + " " + "Loan Approved", accountDestiny, true);
        transactionService.saveTransaction(transaction);
        transaction.setActualBalance(accountDestiny.getBalance() + loanApplicationDTO.getAmount());

        accountDestiny.setBalance(accountDestiny.getBalance() + loanApplicationDTO.getAmount());
        accountService.saveAccount(accountDestiny);

        return new ResponseEntity<>("201 Created",HttpStatus.CREATED);

    }
    @PostMapping("/loans/admin")
    public ResponseEntity<Object> createLoanAdmin(Authentication authentication, @RequestParam String name, @RequestParam int maxAmount, @RequestParam Integer[] payments, @RequestParam double interest_percentaje) {
        Client client = clientService.findByEmail(authentication.getName());
        if(client.getFirstName().equals("admin")) {
            List<Integer> newList = Arrays.stream(payments).collect(Collectors.toList());
            Loan newLoan = new Loan(name, maxAmount, newList, interest_percentaje);
            loanService.saveLoan(newLoan);
            return new ResponseEntity<>("New loan Create",HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Something go wrong",HttpStatus.FORBIDDEN);
        }
    }
}
