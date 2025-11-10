package org.example.userservice.domain.exceptions;

public class UserAlreadyAssociatedException extends RuntimeException {
    public UserAlreadyAssociatedException(Long userId, Long accountId) {
        super("The user with ID " +  userId + " is already associated with the account with ID " + accountId);
    }
}
