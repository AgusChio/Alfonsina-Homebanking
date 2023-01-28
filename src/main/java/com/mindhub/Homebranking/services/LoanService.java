package com.mindhub.Homebranking.services;

import com.mindhub.Homebranking.dto.LoanDTO;
import com.mindhub.Homebranking.models.Account;
import com.mindhub.Homebranking.models.Loan;

import java.util.List;

public interface LoanService {

    List<Loan> findAllLoans();

    List<LoanDTO> loanToLoanDTO(List<Loan> loans);

    Loan getLoansById(Long id);

    void saveLoan(Loan loan);
}
