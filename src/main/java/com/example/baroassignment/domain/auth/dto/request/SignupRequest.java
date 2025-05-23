package com.example.baroassignment.domain.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequest {

    private String username;

    private String password;

    private String nickname;
}
