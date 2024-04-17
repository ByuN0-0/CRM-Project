package com.capstone.crmproject.service;


import com.capstone.crmproject.model.User;
import com.capstone.crmproject.model.WorkSpace;
import com.capstone.crmproject.repository.UserRepository;
import com.capstone.crmproject.repository.WorkSpaceRepository;
import com.capstone.crmproject.request.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService{
    private final UserRepository userRepository;
    private final com.capstone.crmproject.repository.WorkSpaceRepository WorkSpaceRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, WorkSpaceRepository workSpaceRepository,BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.WorkSpaceRepository = workSpaceRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public ResponseEntity<String> registerUser(UserRegisterRequest URRequest){
        //Todo
        if (userRepository.existsByUserName(URRequest.getUserName())) {
            return ResponseEntity.badRequest().body("{\"message\": \"User already exists\"}");
        }
        User user = User.builder()
                .userName(URRequest.getUserName())
                .password(passwordEncoder.encode(URRequest.getPassword()))
                .userNick(URRequest.getUserNick())
                .role("ROLE_USER")
                .build();

        userRepository.save(user);

        WorkSpace workSpace = WorkSpace.builder()
                .name(URRequest.getWorkSpaceName())
                .owner(URRequest.getUserName())
                .memberId(URRequest.getUserName())
                .build();
        WorkSpaceRepository.save(workSpace);
        return ResponseEntity.ok().body("{\"message\": \"User registered successfully\"}");
    }
}
