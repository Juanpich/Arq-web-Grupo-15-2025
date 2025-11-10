package org.example.scooterservice.domain.entities;

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