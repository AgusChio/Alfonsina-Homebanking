package com.mindhub.Homebranking.services;

import com.mindhub.Homebranking.dto.AccountDTO;
import com.mindhub.Homebranking.models.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAllAccounts();

    List<AccountDTO> accountToAccountsDTO(List<Account> accounts);

    AccountDTO accountToAccountsDTO(Account account);

    Account getAccountsById(Long id);

    Account findByNumberAccount(String number);

    void saveAccount(Account account);


}
