package com.mindhub.Homebranking.services.impl;

import com.mindhub.Homebranking.dto.AccountDTO;
import com.mindhub.Homebranking.models.Account;
import com.mindhub.Homebranking.repositories.AccountRepository;
import com.mindhub.Homebranking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> findAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public List<AccountDTO> accountToAccountsDTO(List<Account> accounts) {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
    }

    @Override
    public AccountDTO accountToAccountsDTO(Account account) {
        return new AccountDTO(account);
    }

    @Override
    public Account getAccountsById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account findByNumberAccount(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }
}
