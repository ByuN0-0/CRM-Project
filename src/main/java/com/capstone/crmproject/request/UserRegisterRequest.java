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

    public UserRegisterRequest() {
    }

    public UserRegisterRequest(String userName, String password, String userNick, String workSpaceName, String role) {
        this.user = new User(); //Todo
        this.user.setUserName(userName);
        this.user.setPassword(password);
        this.user.setUserNick(userNick);
        this.user.setRole(role);
        this.workSpaceName = workSpaceName;
    }
}
