package com.example.baroassignment.domain.admin.service;

import com.example.baroassignment.domain.User;
import com.example.baroassignment.domain.auth.dto.response.UserResponse;
import com.example.baroassignment.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public UserResponse grantAdminRole(Long userId) {
        User user = userRepository.findById(userId);
        user.updateToAdmin();
        return UserResponse.from(user);
    }
}
