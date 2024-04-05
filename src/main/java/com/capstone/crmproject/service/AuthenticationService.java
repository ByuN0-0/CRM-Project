package com.capstone.crmproject.service;

import com.capstone.crmproject.model.User;
import com.capstone.crmproject.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    public AuthenticationService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository){
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }
    public boolean authenticate(String username, String password){
        User extendedUser = userRepository.findByUserName(username);
        if (extendedUser == null) {
            return false; // 사용자가 존재하지 않음
        } else {
            // 암호화된 비밀번호를 비교하여 일치하는지 확인
            return passwordEncoder.matches(password, extendedUser.getPassword());
        }
    }
}
