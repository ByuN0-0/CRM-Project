package com.capstone.crmproject.service;

import com.capstone.crmproject.model.CustomUserDetails;
import com.capstone.crmproject.model.User;
import com.capstone.crmproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userData = userRepository.findByUserName(username);
        if (userData != null) {
            return new CustomUserDetails(userData);
        }

        return null;
    }
}
