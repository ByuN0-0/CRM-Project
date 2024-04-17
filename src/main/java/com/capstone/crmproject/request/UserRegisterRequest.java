package com.capstone.crmproject.request;

import com.capstone.crmproject.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {

    private String userName;
    private String password;
    private String userNick;
    private String role;
    private String workSpaceName;
    private User user;
}
