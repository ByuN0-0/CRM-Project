package com.capstone.crmproject.controller;

import com.capstone.crmproject.model.User;
import com.capstone.crmproject.service.UserService;
import com.capstone.crmproject.service.UserServiceImpl;
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
    @PostMapping("/registerProc")
    @ResponseBody
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userService.registerUser(user)){
            return ResponseEntity.ok().body("{\"message\": \"User registered successfully\"}");
        }
        else{
            return ResponseEntity.badRequest().body("{\"message\": \"User already exists\"}");
        }
    }
}
