package com.example.baroassignment.domain.auth.service;

import com.example.baroassignment.domain.User;
import com.example.baroassignment.domain.auth.dto.request.SigninRequest;
import com.example.baroassignment.domain.auth.dto.request.SignupRequest;
import com.example.baroassignment.domain.auth.dto.response.SigninResponse;
import com.example.baroassignment.domain.auth.dto.response.UserResponse;
import com.example.baroassignment.domain.auth.enums.UserRole;
import com.example.baroassignment.domain.auth.repository.UserRepository;
import com.example.baroassignment.global.config.IdGenerator;
import com.example.baroassignment.global.exception.CustomException;
import com.example.baroassignment.global.exception.ErrorCode;
import com.example.baroassignment.global.jwt.JwtUtil;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthService authService;

    @Nested
    class signupTests{
        @Test
        void 회원가입_성공(){
            // Given
            SignupRequest signupRequest = new SignupRequest("testUser", "password", "nickname");

            // When
            UserResponse userResponse = authService.signup(signupRequest);

            // Then
            assertEquals("testUser", userResponse.getUsername());
            assertEquals("nickname", userResponse.getNickname());
            verify(userRepository, times(1)).findByUsername("testUser");
            verify(passwordEncoder, times(1)).encode("password");
            verify(userRepository, times(1)).save(any(User.class));
        }
        @Test
        void 회원가입_실패_이미_존재하는_이름(){
            // Given
            String username = "test";
            SignupRequest signupRequest = new SignupRequest(username, "nickname", "1234");
            User existingUser = new User(1L, username, "existingNickname", "encodedPassword");
            given(userRepository.findByUsername(username)).willReturn(Optional.of(existingUser));

            // When
            CustomException exception = assertThrows(CustomException.class, () -> authService.signup(signupRequest));

            // Then
            assertEquals(ErrorCode.USER_ALREADY_EXISTS, exception.getErrorCode());
            verify(userRepository, times(1)).findByUsername(username);
            verify(idGenerator, never()).generateId();
            verify(passwordEncoder, never()).encode(anyString());
            verify(userRepository, never()).save(any(User.class));
        }
    }

    @Nested
    class signinTests{
        @Test
        void 로그인_성공(){
            // Given
            SigninRequest request = new SigninRequest("testuser", "password123");

            User user = new User(
                    1L,
                    "testuser",
                    "tester",
                    "encodedPassword"
            );

            when(userRepository.findAll()).thenReturn(List.of(user));
            when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
            when(jwtUtil.createToken(1L, "testuser", "tester", user.getRole().toString())).thenReturn("Bearer token");
            when(jwtUtil.substringToken("Bearer token")).thenReturn("token");

            // When
            SigninResponse response = authService.signin(request);

            // Then
            assertEquals("token", response.getToken());
        }
        @Test
        void 로그인_실패_존재하지_않는_아이디(){
            // Given
            SigninRequest request = new SigninRequest("notfound", "password");

            when(userRepository.findAll()).thenReturn(Collections.emptyList());

            // When
            CustomException e = assertThrows(CustomException.class, () ->
                    authService.signin(request)
            );
            // Then
            assertEquals(ErrorCode.INVALID_CREDENTIALS, e.getErrorCode());
        }
        @Test
        void 로그인_실패_비밀번호_불일치(){
            // given
            SigninRequest request = new SigninRequest("testuser", "wrongpassword");

            User user = new User(
                    1L,
                    "testuser",
                    "tester",
                    "encodedPassword"
            );
            // When
            when(userRepository.findAll()).thenReturn(List.of(user));
            when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

            // expect
            CustomException e = assertThrows(CustomException.class, () ->
                    authService.signin(request)
            );
            // Then
            assertEquals(ErrorCode.INVALID_CREDENTIALS, e.getErrorCode());
        }
    }
}