package com.capstone.crmproject.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    //@GetMapping("/login")
    //public String login() {
        //return "login";
    //}
    /*
    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/api/login")
    @ResponseBody
    public ResponseEntity<String> loginProc(@RequestBody LoginForm loginForm) {
        System.out.println("여기 유저네임: " + loginForm.getUsername());
        if (authenticationService.authenticate(loginForm.getUsername(), loginForm.getPassword())) {
            return ResponseEntity.ok().body("{\"message\": \"User authenticated successfully\"}");
        }
        return ResponseEntity.badRequest().body("{\"message\": \"User authentication failed\"}");
    }

     */

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }
}
