package com.pocketbook.userservice.exceptions;

import com.pocketbook.userservice.payload.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(UserNamePresentException.class)
    public ResponseEntity<APIResponse> userNamePresentException(UserNamePresentException ex) {
        String message = ex.getMessage();
        APIResponse response = APIResponse.builder()
                .message(message)
                .httpStatus(HttpStatus.FOUND)
                .build();

        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }

    @ExceptionHandler(UserEmailPresentException.class)
    public ResponseEntity<APIResponse> userEmailPresentException(UserEmailPresentException ex) {
        String message = ex.getMessage();
        APIResponse response = APIResponse.builder()
                .message(message)
                .httpStatus(HttpStatus.FOUND)
                .build();

        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }


    public ResponseEntity<APIResponse> userNotFoundException(UserNotFoundException ex) {
        String message = ex.getMessage();
        APIResponse response = APIResponse.builder()
                .message(message)
                .httpStatus(HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
