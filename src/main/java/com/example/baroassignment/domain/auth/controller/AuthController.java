package com.example.baroassignment.domain.auth.controller;

import com.example.baroassignment.domain.auth.dto.request.SignupRequest;
import com.example.baroassignment.domain.auth.dto.response.SignupResponse;
import com.example.baroassignment.domain.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest){

        return new ResponseEntity<>(authService.signup(signupRequest), HttpStatus.CREATED);
    }
}
