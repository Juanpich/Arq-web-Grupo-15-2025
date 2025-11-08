package org.example.scooterservice.application.repositories;


import org.example.scooterservice.domain.entities.Scooter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ScooterRepository extends MongoRepository<Scooter,Long> {

    @Query("{'scooter_id' : ?0}")
    Optional<Scooter> findByScooterId(Long id);
    @Query(value = "{'scooter_id': ?0}", delete = true)
    void deleteByScooter_id(Long id);
}