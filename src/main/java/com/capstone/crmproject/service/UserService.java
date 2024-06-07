package com.capstone.crmproject.service;


import com.capstone.crmproject.dto.RegisterUserDTO;
import com.capstone.crmproject.entity.UserEntity;
import com.capstone.crmproject.entity.WorkspaceEntity;
import com.capstone.crmproject.repository.UserRepository;
import com.capstone.crmproject.security.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserEntity registerUser(RegisterUserDTO registerUserDTO, WorkspaceEntity workspaceEntity){
        //Todo
        if (userRepository.existsByUsername(registerUserDTO.getUsername())) {
            throw new IllegalArgumentException("Given user already exists");
        }

        UserEntity user = new UserEntity(
                registerUserDTO.getUsername(),
                passwordEncoder.encode(registerUserDTO.getPassword()),
                UserRole.ROLE_USER,
                workspaceEntity
        );

        return userRepository.save(user);
    }

    @Transactional
    public UserEntity getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
