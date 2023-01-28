package com.mindhub.Homebranking.services.impl;

import com.mindhub.Homebranking.dto.LoanDTO;
import com.mindhub.Homebranking.models.Loan;
import com.mindhub.Homebranking.repositories.LoanRepository;
import com.mindhub.Homebranking.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Override
    public List<Loan> findAllLoans() {
        return loanRepository.findAll();
    }

    @Override
    public List<LoanDTO> loanToLoanDTO(List<Loan> loans) {
        return loanRepository.findAll().stream().map( loan -> new LoanDTO(loan)).collect(Collectors.toList());
    }

    @Override
    public Loan getLoansById(Long id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }
}
