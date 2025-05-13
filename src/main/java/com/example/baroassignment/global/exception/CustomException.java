package com.example.baroassignment.global.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
public class CustomException extends RuntimeException {
    private final HttpStatus status;
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = errorCode.getStatus();
        this.errorCode = errorCode;
    }

    public CustomException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage() + " " + message);
        this.status = errorCode.getStatus();
        this.errorCode = errorCode;
    }
}
