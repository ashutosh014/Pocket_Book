package com.pocketbook.userservice.payload;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class APIResponse {

    private String message;
    private HttpStatus httpStatus;
}
