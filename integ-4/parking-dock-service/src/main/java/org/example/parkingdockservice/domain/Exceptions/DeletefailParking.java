package org.example.parkingdockservice.domain.Exceptions;

public class DeletefailParking extends RuntimeException {
    public DeletefailParking(String message) {
        super("La parada no pudo ser eliminada.");
    }
}
