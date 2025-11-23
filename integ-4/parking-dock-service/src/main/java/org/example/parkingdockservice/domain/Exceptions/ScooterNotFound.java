package org.example.parkingdockservice.domain.Exceptions;

public class ScooterNotFound extends RuntimeException {
    public ScooterNotFound(Long scooter_id) {

        super("No existe el scooter con este id " + scooter_id);
    }
}
