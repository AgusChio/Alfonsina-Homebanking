package com.mindhub.Homebranking.controllers;

import com.mindhub.Homebranking.models.*;
import com.mindhub.Homebranking.services.AccountService;
import com.mindhub.Homebranking.services.CardService;
import com.mindhub.Homebranking.services.ClientService;
import com.mindhub.Homebranking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;


    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardColor color,
                                             @RequestParam CardType type,
                                             @RequestParam String accountSelect ,
                                             Authentication authentication) {
        Client client = clientService.findByEmail(authentication.getName());
        Account accountCurrent = accountService.findByNumberAccount(accountSelect);
        Set<Card> cards = client.getCards().stream().filter(card -> card.isActive_card()).collect(Collectors.toSet());
        List<Card> cardsType = cards.stream().filter(card -> card.isActive_card()).filter(card -> card.getType() == type).collect(Collectors.toList());

        if (accountCurrent == null || !accountCurrent.isActive_account()){
            return new ResponseEntity<>("403 forbidden", HttpStatus.FORBIDDEN);
        }

        if (client.getAccounts().stream().noneMatch(account -> account.getId() == accountCurrent.getId())){
            return new ResponseEntity<>("The account doesn't belong to this client", HttpStatus.FORBIDDEN);
        }

        if (cards.size() == 6) {
            return new ResponseEntity<>("403 forbidden", HttpStatus.FORBIDDEN);
        } else {
            if (cardsType.size() == 3) {
                return new ResponseEntity<>("403 forbidden", HttpStatus.FORBIDDEN);
            } else {
                if(cardsType.stream().filter(card1 -> card1.getColor() == color).count() == 1) {
                    return new ResponseEntity<>("409 Conflict",HttpStatus.CONFLICT);
                } else {
                    Card card = new Card(client.getFirstName() + " " + client.getLastName(), CardUtils.getCardNumber(), CardUtils.getNumberCVV(), LocalDate.now(),LocalDate.now().plusYears(5), type,color, client, true, accountCurrent);
                    cardService.saveCard(card);
                    return  new ResponseEntity<>("201 Created",HttpStatus.CREATED);
                }
            }
        }
    }

    @PatchMapping("/clients/current/cards/{id}")
    public ResponseEntity<Object> deleteCard(Authentication authentication, @PathVariable long id){
        Client client = clientService.findByEmail(authentication.getName());
        Set<Card> cards = client.getCards();
        Card cardsClients = cardService.getCardById(id);
        Card cardFound = cards.stream().filter(card -> card == cardsClients).findFirst().orElse(null);
        cardService.getCardById(cardFound.getId()).setActive_card(false);
        cardService.saveCard(cardFound);
        return new ResponseEntity<>("Delete", HttpStatus.OK);
    }

}

