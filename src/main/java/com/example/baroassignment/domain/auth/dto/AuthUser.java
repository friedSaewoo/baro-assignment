package com.example.baroassignment.domain.auth.dto;

import lombok.Getter;

@Getter
public class AuthUser {

    private final Long id;
    private final String username;
    private final String nickname;

    public AuthUser(Long id, String username, String nickname) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
    }
}