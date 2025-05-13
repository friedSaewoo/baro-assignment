package com.example.baroassignment.domain;

import com.example.baroassignment.domain.auth.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private Long id;
    private String username;
    private String nickname;
    private String password;
    private UserRole role;


    public User(Long id,String username, String nickname, String encodedPassword){
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.password = encodedPassword;
        if(id == 1L){
            this.role=UserRole.ROLE_ADMIN;
        }else{
            this.role=UserRole.ROLE_USER;
        }
    }

    public void updateToAdmin(){
        if (this.role != UserRole.ROLE_ADMIN) {
            this.role = UserRole.ROLE_ADMIN;
        }
    }
}
