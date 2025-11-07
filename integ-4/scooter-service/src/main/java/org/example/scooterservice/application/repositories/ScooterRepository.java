package org.example.scooterservice.application.repositories;


import org.example.scooterservice.domain.entities.Scooter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScooterRepository extends MongoRepository<Scooter,Long> {


}