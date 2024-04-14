package com.capstone.crmproject.controller;

import com.capstone.crmproject.request.UserRegisterRequest;
import com.capstone.crmproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String join() {
        return "join";
    }
    @PostMapping("/api/registerProc")
    @ResponseBody
    public ResponseEntity<String> registerUser(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userService.registerUser(userRegisterRequest)){
            System.out.println("User registered successfully");
            return ResponseEntity.ok().body("{\"message\": \"User registered successfully\"}");
        }
        else{
            System.out.println("User already exists");
            return ResponseEntity.badRequest().body("{\"message\": \"User already exists\"}");
        }
    }
}