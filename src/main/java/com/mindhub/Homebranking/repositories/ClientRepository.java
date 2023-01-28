package com.mindhub.Homebranking.repositories;

import com.mindhub.Homebranking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client , Long> { //que tipo de dato manipula en el repositorio//
    Client findByEmail (String email);
}

