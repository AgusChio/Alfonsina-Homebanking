package com.mindhub.Homebranking.dto;

import com.mindhub.Homebranking.models.Client;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private long id;
    private String firstName, lastName, email;
    private List<AccountDTO> accounts;
    private List<ClientLoanDTO> loans;
    private Set<CardDTO> cards;

    public ClientDTO(Client client){
        id = client.getId();
        firstName = client.getFirstName();
        lastName = client.getLastName();
        email = client.getEmail();
        accounts = client.getAccounts().stream().filter(account -> account.isActive_account() == true).map(account -> new AccountDTO(account)).collect(Collectors.toList());
        loans = client.getClientLoans().stream().map(clientloan -> new ClientLoanDTO(clientloan)).collect(Collectors.toList());
        cards = client.getCards().stream().filter(card -> card.isActive_card()== true).map(cards -> new CardDTO(cards)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public List<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}
