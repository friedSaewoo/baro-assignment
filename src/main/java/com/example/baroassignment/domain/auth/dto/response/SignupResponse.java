package com.example.baroassignment.domain.auth.dto.response;

import com.example.baroassignment.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class SignupResponse {
    private String username;
    private String nickname;
    private List<Map<String, String>> roles;

    public static SignupResponse from(User user) {
        Map<String, String> roleMap = new HashMap<>();
        roleMap.put("role", user.getRole().name());
        return new SignupResponse(
                user.getUsername(),
                user.getNickname(),
                List.of(roleMap)
        );
    }
}
