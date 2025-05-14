package com.example.baroassignment.domain.admin.service;

import com.example.baroassignment.domain.User;
import com.example.baroassignment.domain.auth.dto.response.UserResponse;
import com.example.baroassignment.domain.auth.repository.UserRepository;
import com.example.baroassignment.global.exception.CustomException;
import com.example.baroassignment.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public UserResponse grantAdminRole(Long userId) {
        User user = userRepository.findById(userId);
        if(user == null){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        user.updateToAdmin();
        return UserResponse.from(user);
    }
}
