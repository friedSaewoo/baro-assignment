package com.example.baroassignment.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class CustomExceptionResponse {

    private final ErrorInfo error;


    public CustomExceptionResponse(ErrorCode errorCode) {
        this.error = new ErrorInfo(errorCode.name(), errorCode.getMessage());
    }
}
