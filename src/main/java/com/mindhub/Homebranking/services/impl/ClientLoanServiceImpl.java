package com.mindhub.Homebranking.services.impl;

import com.mindhub.Homebranking.models.ClientLoan;
import com.mindhub.Homebranking.repositories.ClientLoanRepository;
import com.mindhub.Homebranking.services.ClientLoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServiceImpl implements ClientLoanService {

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Override
    public void saveClientLoans(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }
}
