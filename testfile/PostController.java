package com.capstone.crmproject.testfile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class PostController {
    @PostMapping("/api/post")
    public String postUser() {
        return "Post User";
    }
}
