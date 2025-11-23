package org.example.movementservice.domain.exceptions;

public class UserNotAssociatedWithTheAccount extends RuntimeException {
    public UserNotAssociatedWithTheAccount(Long id_account, Long id_user) {
        super("User "+id_user+" is not associated with "+id_account);
    }
}
