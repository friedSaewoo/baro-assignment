package com.example.baroassignment.domain.auth.service;

import com.example.baroassignment.domain.User;
import com.example.baroassignment.domain.auth.dto.request.SignupRequest;
import com.example.baroassignment.domain.auth.dto.response.SignupResponse;
import com.example.baroassignment.global.exception.CustomException;
import com.example.baroassignment.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final Map<Long, User> userRepository = new HashMap<>();
    private static Long nextId = 1L;

    public SignupResponse signup(SignupRequest signupRequest) {
        if (isUserNameAlreadyRegistered(signupRequest.getUsername())){
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
        }
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getNickname(),
                encodedPassword
        );
        Map<String, String> roles = new HashMap<>();
        if(nextId == 1L){
            roles.put("role","ROLE_ADMIN");
        }
        else{
            roles.put("role","ROLE_USER");
        }
        user.getRoles().add(roles);
        userRepository.put(nextId,user);
        nextId++;
        return new SignupResponse(user.getUsername(),user.getNickname(),user.getRoles());
    }

    private boolean isUserNameAlreadyRegistered(String username) {
        return userRepository.values().stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }
}
