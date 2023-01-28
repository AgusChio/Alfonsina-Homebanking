package com.mindhub.Homebranking.services;

import com.mindhub.Homebranking.dto.ClientDTO;
import com.mindhub.Homebranking.models.Client;
import java.util.List;

public interface ClientService {

    List<Client> findAllClients();

    List<ClientDTO> clientsToClientsDTO(List<Client> clients);

    Client getClientsById(Long id);

    Client findByEmail(String email);

    void saveClients(Client client);

    ClientDTO clientsToClientsDTO(Client client);
}
