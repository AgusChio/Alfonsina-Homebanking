package com.mindhub.Homebranking.repositories;

import com.mindhub.Homebranking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository  extends JpaRepository<Card, Long> {
    Card findByNumber(String number);
}
