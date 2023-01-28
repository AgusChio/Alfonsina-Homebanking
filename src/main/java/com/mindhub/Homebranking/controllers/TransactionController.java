package com.mindhub.Homebranking.controllers;

import com.mindhub.Homebranking.dto.CardApplicationDTO;
import com.mindhub.Homebranking.dto.PdfGeneratorDTO;
import com.mindhub.Homebranking.models.*;
import com.mindhub.Homebranking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CardService cardService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(Authentication authentication,
                                                    @RequestParam double amount,
                                                    @RequestParam String description,
                                                    @RequestParam String oAccount,
                                                    @RequestParam String dAccount)
    {
        Client client = clientService.findByEmail(authentication.getName());
        Account originAccount = accountService.findByNumberAccount(oAccount);
        Account destinyAccount = accountService.findByNumberAccount(dAccount);
        Set<Account> accountExist =  client.getAccounts().stream().filter(account1 -> account1.getNumber().equals(originAccount)).collect(Collectors.toSet());

        if (amount == 0) {
            return new ResponseEntity<>("Missing Amount", HttpStatus.EXPECTATION_FAILED);
        }
        if (description.isEmpty()) {
            return new ResponseEntity<>("Missing Description", HttpStatus.FORBIDDEN);
        }
        if (oAccount.isEmpty() ) {
            return new ResponseEntity<>("Missing Origin Account", HttpStatus.FORBIDDEN);
        }
        if (dAccount.isEmpty()) {
            return new ResponseEntity<>("Missing Destiny Account", HttpStatus.FORBIDDEN);
        }

        if (oAccount.equals(dAccount)){
            return new ResponseEntity<>("Origin Account can't be the same as Destiny Account", HttpStatus.FORBIDDEN);
        }
        if(originAccount == null){
            return new ResponseEntity<>("Your origin account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if(destinyAccount == null){
            return new ResponseEntity<>("Your Destiny Account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (accountExist.size() != 0) {
            return new ResponseEntity<>("The account number does not belong to you", HttpStatus.FORBIDDEN);
        }

        if(originAccount.getBalance() < amount){
            return new ResponseEntity<>("Not enough balance", HttpStatus.FORBIDDEN);
        }
            Transaction transactionDebit = new Transaction(amount,TransactionType.DEBIT, LocalDateTime.now(), description + " " + originAccount.getNumber(), originAccount, true);
            transactionService.saveTransaction(transactionDebit);
            transactionDebit.setActualBalance(originAccount.getBalance() - amount);
            originAccount.setBalance(originAccount.getBalance() - amount);
            accountService.saveAccount(originAccount);

            Transaction transactionCredit = new Transaction(amount, TransactionType.CREDIT, LocalDateTime.now(), description + " " + destinyAccount.getNumber(),destinyAccount, true);
            transactionService.saveTransaction(transactionCredit);
            transactionCredit.setActualBalance(originAccount.getBalance() + amount);
            destinyAccount.setBalance(destinyAccount.getBalance() + amount);
            accountService.saveAccount(destinyAccount);

        return new ResponseEntity<>("201 Created", HttpStatus.CREATED);

    }

    @Transactional
    @PostMapping("/transactions/payments")
    public ResponseEntity<Object> MakePay(Authentication authentication, @RequestBody CardApplicationDTO cardApplicationDTO){
        Client client = clientService.findByEmail(authentication.getName());
        Card cardNumber = cardService.getCardByNumber(cardApplicationDTO.getNumber());
        Account account = accountService.getAccountsById(cardNumber.getAccount().getId());

        if (client == null){
            return new ResponseEntity<>("The client does not exist", HttpStatus.FORBIDDEN);
        }

        if (cardApplicationDTO.getNumber().isEmpty()){
            return new ResponseEntity<>("Invalid number of the card", HttpStatus.FORBIDDEN);
        }

        if (cardNumber == null){
            return new ResponseEntity<>("The number does not belong to that card", HttpStatus.FORBIDDEN);
        }

        if (cardApplicationDTO.getCvv() < 100 || cardApplicationDTO.getCvv() > 999){
            return new ResponseEntity<>("Invalid CVV", HttpStatus.FORBIDDEN);
        }

        if (!Objects.equals(cardApplicationDTO.getCvv(), cardNumber.getCvv())){
            return new ResponseEntity<>("The Cvv does not belong to that card", HttpStatus.FORBIDDEN);
        }

        if (client.getCards().stream().noneMatch(card -> card.getId() == cardNumber.getId())){
            return new ResponseEntity<>("the card does not belong to this client", HttpStatus.FORBIDDEN);
        }

        if (account.getBalance() < cardApplicationDTO.getAmount()){
            return new ResponseEntity<>("Insufficient amount", HttpStatus.FORBIDDEN);
        }

        if (LocalDate.now().isAfter(cardApplicationDTO.getThruDate())){
            return new ResponseEntity<>("the card is expired", HttpStatus.FORBIDDEN);
        }

        if (cardApplicationDTO.getDescription() == ""){
            return new ResponseEntity<>("Complete the field description", HttpStatus.FORBIDDEN);
        }

        Transaction transaction = new Transaction(cardApplicationDTO.getAmount(),TransactionType.DEBIT, LocalDateTime.now(), cardApplicationDTO.getDescription(), account, true);
        transaction.setActualBalance(account.getBalance() - cardApplicationDTO.getAmount() );
        account.setBalance(account.getBalance() - cardApplicationDTO.getAmount());
        transactionService.saveTransaction(transaction);
        accountService.saveAccount(account);
        

         return new ResponseEntity<>("Payment successful",HttpStatus.CREATED);
    }


    @PostMapping("/generatePdf")
    public  ResponseEntity<Object> createPdfWithTransactions(Authentication authentication,
                                                             @RequestBody PdfGeneratorDTO pdfGeneratorDTO,
                                                             HttpServletResponse httpServletResponse) throws Exception{
        Client clientAuth = clientService.findByEmail(authentication.getName());
        Account accountAuth = accountService.findByNumberAccount(pdfGeneratorDTO.getAccountNumber());
        Set<Transaction> transactionsAuth = accountAuth.getTransactions();

        if (clientAuth == null){
            return new ResponseEntity<>("The client doesn't exist",HttpStatus.FORBIDDEN);
        }

        if (accountAuth == null){
            return new ResponseEntity<>("The account doesn't exist",HttpStatus.FORBIDDEN);
        }

        if (pdfGeneratorDTO.getStartDate() == null || pdfGeneratorDTO.getEndDate() == null){
            return new ResponseEntity<>("Invalid Dates",HttpStatus.FORBIDDEN);
        }

        if (pdfGeneratorDTO.getStartDate().isAfter(pdfGeneratorDTO.getEndDate())){
            return new ResponseEntity<>("Invalid Dates",HttpStatus.FORBIDDEN);
        }

        Set<Transaction> transactionsByDate = transactionService.filterTransactionByDate(pdfGeneratorDTO.getStartDate(), pdfGeneratorDTO.getEndDate(), transactionsAuth);

        GeneratePDF.createPDF(transactionsByDate, clientAuth,httpServletResponse);

        return new ResponseEntity<>("PDF created", HttpStatus.CREATED);

    }


}
