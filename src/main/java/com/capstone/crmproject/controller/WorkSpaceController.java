package com.capstone.crmproject.controller;

import com.capstone.crmproject.request.AddMemberRequest;
import com.capstone.crmproject.service.WorkSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WorkSpaceController {
    private final WorkSpaceService workSpaceService;
    @Autowired
    public WorkSpaceController(WorkSpaceService workSpaceService) {
        this.workSpaceService = workSpaceService;
    }
    @PostMapping("/api/addWorkMember")
    @ResponseBody // Todo
    public ResponseEntity<String> addMember(@RequestBody AddMemberRequest member) {

        if (workSpaceService.addMember(member)){
            System.out.println("User registered successfully");
            return ResponseEntity.ok().body("{\"message\": \"User registered successfully\"}");
        }
        else{
            System.out.println("User already exists");
            return ResponseEntity.badRequest().body("{\"message\": \"User already exists\"}");
        }
    }
}
