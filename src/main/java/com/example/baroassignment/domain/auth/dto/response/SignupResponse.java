package com.example.baroassignment.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class SignupResponse {
    private String username;
    private String  nickname;
    private List<Map<String, String>> roles;
}
