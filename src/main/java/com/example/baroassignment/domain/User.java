package com.example.baroassignment.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class User {
    private String username;
    private String nickname;
    private String password;
    private List<Map<String, String>> roles = new ArrayList<>();

    public User(String username, String nickname, String encodedPassword){
        this.username=username;
        this.nickname=nickname;
        this.password=encodedPassword;
    }
}
