package com.capstone.crmproject.service;


import com.capstone.crmproject.model.User;
import com.capstone.crmproject.model.UserRole;
import com.capstone.crmproject.model.WorkSpace;
import com.capstone.crmproject.repository.UserRepository;
import com.capstone.crmproject.repository.WorkSpaceRepository;
import com.capstone.crmproject.request.UserRegisterRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, WorkSpaceRepository workSpaceRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User registerUser(UserRegisterRequest userRegisterRequest) {
        //Todo
        if (userRepository.existsByUserName(userRegisterRequest.getUserName())) {
            throw new RuntimeException("Given user already exists");
        }

        User user = User.builder()
                .userName(userRegisterRequest.getUserName())
                .password(passwordEncoder.encode(userRegisterRequest.getPassword()))
                .userNick(userRegisterRequest.getUserNick())
                .role(UserRole.ROLE_USER)
                .build();

        return userRepository.save(user);
    }
}
