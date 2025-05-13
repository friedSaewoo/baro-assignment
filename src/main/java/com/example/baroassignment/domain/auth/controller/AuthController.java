package com.example.baroassignment.domain.auth.controller;

import com.example.baroassignment.domain.auth.dto.request.SigninRequest;
import com.example.baroassignment.domain.auth.dto.request.SignupRequest;
import com.example.baroassignment.domain.auth.dto.response.SigninResponse;
import com.example.baroassignment.domain.auth.dto.response.UserResponse;
import com.example.baroassignment.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody SignupRequest signupRequest){

        return new ResponseEntity<>(authService.signup(signupRequest), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest signinRequest){
        return new ResponseEntity<>(authService.signin(signinRequest), HttpStatus.OK);
    }
}
