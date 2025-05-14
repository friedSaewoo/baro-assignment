package com.example.baroassignment.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionResponse> handleCustomException(CustomException e) {
        return getErrorResponse(e.getStatus(), e.getErrorCode());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomExceptionResponse> handleAccessDeniedException(AccessDeniedException e) {
        return getErrorResponse(HttpStatus.FORBIDDEN, ErrorCode.ACCESS_DENIED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String firstErrorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("유효성 검사 오류가 발생했습니다.");
        return getErrorResponse(HttpStatus.BAD_REQUEST, ErrorCode.UNKNOWN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomExceptionResponse> handleAllExceptions(Exception e) {
        log.error("Exception", e);
        return getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.UNKNOWN);
    }

    private ResponseEntity<CustomExceptionResponse> getErrorResponse(HttpStatus status, ErrorCode errorCode) {
        CustomExceptionResponse response = new CustomExceptionResponse(errorCode);
        return new ResponseEntity<>(response, status);
    }
}
