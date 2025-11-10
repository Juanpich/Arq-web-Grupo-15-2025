package org.example.parkingdockservice.domain.Exceptions;

public class DeletefailScooter extends RuntimeException {
    public DeletefailScooter(String message) {
        super("Fallo al eliminar un monopatin de la parada.");
    }
}
