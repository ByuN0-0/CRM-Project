package com.capstone.crmproject.controller;

import com.capstone.crmproject.model.LoginForm;
import com.capstone.crmproject.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    private final AuthenticationService authenticationService;
    public LoginController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @PostMapping("/loginProc")
    @ResponseBody
    public ResponseEntity<String> loginProc(@RequestBody LoginForm loginForm){
        System.out.println("여기 유저네임: "+loginForm.getUserName());
        if(authenticationService.authenticate(loginForm.getUserName(), loginForm.getPassword())){
            return ResponseEntity.ok().body("{\"message\": \"User authenticated successfully\"}");
        }
        return ResponseEntity.badRequest().body("{\"message\": \"User authentication failed\"}");
    }
}
