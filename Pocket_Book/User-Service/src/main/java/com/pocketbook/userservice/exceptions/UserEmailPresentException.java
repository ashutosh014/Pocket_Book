package com.pocketbook.userservice.exceptions;

public class UserEmailPresentException extends Exception {

    public UserEmailPresentException(String message) {
        super(message);
    }
}
