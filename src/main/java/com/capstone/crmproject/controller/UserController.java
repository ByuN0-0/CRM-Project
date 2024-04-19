package com.capstone.crmproject.controller;

import com.capstone.crmproject.model.User;
import com.capstone.crmproject.model.WorkSpace;
import com.capstone.crmproject.request.UserRegisterRequest;
import com.capstone.crmproject.service.UserService;
import com.capstone.crmproject.service.WorkSpaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;
    private final WorkSpaceService workSpaceService;

    public UserController(UserService userService, WorkSpaceService workSpaceService) {
        this.userService = userService;
        this.workSpaceService = workSpaceService;
    }


    @GetMapping("/register")
    public String join() {
        return "join";
    }

    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            User newUser = userService.registerUser(userRegisterRequest);
            WorkSpace newWorkSpace = workSpaceService.createWorkSpace(userRegisterRequest);
            String message = "User registered with ID: " + newUser.getUserName() + ", Workspace created with Name: " + newWorkSpace.getName();
            return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"register failed\"}");
        }
    }
}
