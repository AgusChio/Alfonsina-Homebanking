package com.mindhub.Homebranking.services.impl;

import com.mindhub.Homebranking.dto.ClientDTO;
import com.mindhub.Homebranking.models.Client;
import com.mindhub.Homebranking.repositories.ClientRepository;
import com.mindhub.Homebranking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import static java.util.stream.Collectors.toList;


@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<ClientDTO> clientsToClientsDTO(List<Client> clients) {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(toList());
    }

    @Override
    public Client getClientsById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }


    @Override
    public void saveClients(Client client) {
        clientRepository.save(client);
    }

    @Override
    public ClientDTO clientsToClientsDTO(Client client) {
        return new ClientDTO(client);
    }
}
