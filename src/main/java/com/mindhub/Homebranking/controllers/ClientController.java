package com.mindhub.Homebranking.controllers;

import com.mindhub.Homebranking.dto.ClientDTO;
import com.mindhub.Homebranking.models.Account;
import com.mindhub.Homebranking.models.AccountType;
import com.mindhub.Homebranking.models.Client;
import com.mindhub.Homebranking.services.AccountService;
import com.mindhub.Homebranking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;



@RestController
@RequestMapping("/api") // asociacion entre el request y el controler
public class ClientController {
    @Autowired
    private ClientService clientService; // creo una instacia del repositorio al controlador

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping("/clients")
    public List<ClientDTO> getClients(){
        return clientService.clientsToClientsDTO(clientService.findAllClients());
    };

    @RequestMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.clientsToClientsDTO(clientService.getClientsById(id));
    };

    @PostMapping("/clients")
    public ResponseEntity<Object> register( @RequestParam String firstName,
                                            @RequestParam String lastName,
                                            @RequestParam String email,
                                            @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClients(client);
        Account account = new Account( "VIN-" + (int)(Math.random()*(999999 - 100000) + 100000), LocalDateTime.now(), 0, client,true, AccountType.SAVING);
        accountService.saveAccount(account);
        return new ResponseEntity<>("Register Successfully",HttpStatus.CREATED);
    }

    @RequestMapping("/clients/current")
    public ClientDTO getAll(Authentication authentication) {
        return new ClientDTO(clientService.findByEmail(authentication.getName()));
    }

}
