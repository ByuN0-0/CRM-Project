package com.capstone.crmproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
@Getter
@Setter
public class User {
    private String userName;
    private String password;
    private String userNick;
    private String role;
    public User() {
    }
    public User(String username, String password, String nickname) {
        this.userName = username;
        this.password = password;
        this.userNick = nickname;
    }
}
