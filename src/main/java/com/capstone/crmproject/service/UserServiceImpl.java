package com.capstone.crmproject.service;

import com.capstone.crmproject.model.User;
import com.capstone.crmproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public boolean registerUser(User user){
        if (userRepository.existsByUserName(user.getUserName())) {
            return false;
        }
        User data = new User();
        data.setUserName(user.getUserName());
        data.setPassword(passwordEncoder.encode(user.getPassword()));
        data.setUserNick(user.getUserNick());
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
        return true;
    }
}
