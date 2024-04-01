package com.capstone.crmproject.controller;

import com.capstone.crmproject.dao.UserDao;
import com.capstone.crmproject.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    UserDao userDao;

    @PostMapping("/api/register")
    public ResponseEntity<String> register(@RequestBody UserRegistrationRequest userRegistrationRequest) {
        String userId = userRegistrationRequest.getUserId();
        String password = userRegistrationRequest.getPassword();
        User user = new User(userId, password);
        userDao.insert(user);

        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }
    public static class UserRegistrationRequest {
        private String userId;
        private String password;
        // 추가적인 회원가입 정보

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
