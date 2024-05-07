package com.capstone.crmproject.request;

import lombok.Getter;

@Getter
public class UserRegisterRequest {
    private String username;
    private String password;
    private String userNick;
    private String role;
    private String workSpaceName;
}
