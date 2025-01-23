package com.pocketbook.userservice.exceptions;

public class UserNamePresentException extends Exception{

    public UserNamePresentException(String message) {
        super(message);
    }
}
