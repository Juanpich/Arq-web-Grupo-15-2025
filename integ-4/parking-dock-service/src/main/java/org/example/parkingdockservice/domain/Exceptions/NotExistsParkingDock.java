package org.example.parkingdockservice.domain.Exceptions;

public class NotExistsParkingDock extends RuntimeException {
    public NotExistsParkingDock(String message) {

        super("No existe una parada con este id en la base de datos.");
    }
}
