package org.example.journeysservice.domain.exceptions;

public class JourneyAlreadyFinishedException extends RuntimeException {
    public JourneyAlreadyFinishedException(Long journeyId) {
        super("Journey with ID " + journeyId + " is already finished and cannot be modified");
    }
}

