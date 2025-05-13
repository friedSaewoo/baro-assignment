package com.example.baroassignment.domain.admin.controller;

import com.example.baroassignment.domain.admin.service.AdminService;
import com.example.baroassignment.domain.auth.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {

    private final AdminService adminService;

    @PatchMapping("/users/{userId}/roles")
    public ResponseEntity<UserResponse> grantAdminRole(@PathVariable Long userId){
        log.info("userId={}",userId);
        return new ResponseEntity<>(adminService.grantAdminRole(userId), HttpStatus.OK);
    }
}
