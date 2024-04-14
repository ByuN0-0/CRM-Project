package com.capstone.crmproject.service;

import com.capstone.crmproject.model.User;
import com.capstone.crmproject.model.WorkSpace;
import com.capstone.crmproject.repository.UserRepository;
import com.capstone.crmproject.repository.WorkSpaceRepository;
import com.capstone.crmproject.request.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final WorkSpaceRepository WorkSpaceRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, WorkSpaceRepository workSpaceRepository,BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.WorkSpaceRepository = workSpaceRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public boolean registerUser(UserRegisterRequest URRequest){
        User user = new User(URRequest);
        String userName = user.getUserName();
        if (userRepository.existsByUserName(user.getUserName())) {
            return false;
        }
        User data = new User();
        data.setUserName(userName);
        data.setPassword(passwordEncoder.encode(user.getPassword()));
        data.setUserNick(userName);
        data.setRole("ROLE_USER");
        userRepository.save(data);

        WorkSpace workSpace = new WorkSpace();
        workSpace.setName(URRequest.getWorkSpaceName());
        workSpace.setOwner(userName);
        workSpace.getMemberIds().add(user.getUserName());
        WorkSpaceRepository.save(workSpace);
        return true;
    }
}
