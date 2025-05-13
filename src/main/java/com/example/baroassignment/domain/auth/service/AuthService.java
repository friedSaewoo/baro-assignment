package com.example.baroassignment.domain.auth.service;

import com.example.baroassignment.domain.User;
import com.example.baroassignment.domain.auth.dto.request.SigninRequest;
import com.example.baroassignment.domain.auth.dto.request.SignupRequest;
import com.example.baroassignment.domain.auth.dto.response.SigninResponse;
import com.example.baroassignment.domain.auth.dto.response.UserResponse;
import com.example.baroassignment.domain.auth.repository.UserRepository;
import com.example.baroassignment.global.config.IdGenerator;
import com.example.baroassignment.global.exception.CustomException;
import com.example.baroassignment.global.exception.ErrorCode;
import com.example.baroassignment.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final IdGenerator idGenerator;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserResponse signup(SignupRequest signupRequest) {
        if (isUserNameAlreadyRegistered(signupRequest.getUsername())){
            throw new CustomException(ErrorCode.USER_ALREADY_EXISTS);
        }
        String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());
        User user = new User(
                idGenerator.generateId(),
                signupRequest.getUsername(),
                signupRequest.getNickname(),
                encodedPassword
        );

       userRepository.save(user);
        return UserResponse.from(user);
    }

    public SigninResponse signin(SigninRequest signinRequest) {
        User user = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(signinRequest.getUsername()))
                .findFirst()
                .orElseThrow(()-> new CustomException(ErrorCode.INVALID_CREDENTIALS));

        if(!passwordEncoder.matches(signinRequest.getPassword(),user.getPassword())){
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        String token = jwtUtil.createToken(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getRole().toString()
        );
        token = jwtUtil.substringToken(token);

        return new SigninResponse(token);
    }

    private boolean isUserNameAlreadyRegistered(String username) {
        return userRepository.findByUsername(username).stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }
}
