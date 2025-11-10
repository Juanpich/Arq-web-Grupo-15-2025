package org.example.scooterservice.domain.entities;

<<<<<<< HEAD
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scooter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scooter_id;

}
=======

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "scooter")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scooter{

    @Id
    private ObjectId id;
    private Long scooter_id;
    private State state;
    private String gps;
    private Long parkingDockId;

}
>>>>>>> a1a2112804e417048cc4de7f5d1e07aef6b65b71
