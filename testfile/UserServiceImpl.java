/*
package com.capstone.crmproject.testfile;

import com.capstone.crmproject.model.User;
import com.capstone.crmproject.model.WorkSpace;
import com.capstone.crmproject.repository.UserRepository;
import com.capstone.crmproject.repository.WorkSpaceRepository;
import com.capstone.crmproject.request.UserRegisterRequest;
import com.capstone.crmproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


public class UserServiceImpl {

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
    public ResponseEntity<String> registerUser(UserRegisterRequest URRequest){
        User user = new User(URRequest);
        String userName = user.getUsername();
        if (userRepository.existsByUserName(user.getUsername())) {
            return ResponseEntity.badRequest().body("{\"message\": \"User already exists\"}");
        }
        //Todo builder패턴으로 바꾸기
        User data = new User();
        data.setUserName(userName);
        data.setPassword(passwordEncoder.encode(user.getPassword()));
        data.setUserNick(userName);
        data.setRole("ROLE_USER");
        userRepository.save(data);

        WorkSpace workSpace = new WorkSpace();
        workSpace.setName(URRequest.getWorkSpaceName());
        workSpace.setOwner(userName);
        workSpace.getMemberIds().add(user.getUsername());
        WorkSpaceRepository.save(workSpace);
        return ResponseEntity.ok().body("{\"message\": \"User registered successfully\"}");
    }
}
*/
// UserService 인터페이스의 구현체 였던 것
 
