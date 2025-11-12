package org.example.journeysservice.domain.exceptions;

public class UnfinishedJourneyException extends RuntimeException {
    public UnfinishedJourneyException(Long id) {
        super("journey with ID "+ id +" not completed");
    }
}
