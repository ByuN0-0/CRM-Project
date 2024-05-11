package com.capstone.crmproject.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
public class RegisterUserDTO {
    private String username;
    private String password;
    private String workspaceName;
}
