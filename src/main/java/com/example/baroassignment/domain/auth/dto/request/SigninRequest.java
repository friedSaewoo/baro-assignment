package com.example.baroassignment.domain.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SigninRequest {
    private final String username;
    
    private final String password;
}
