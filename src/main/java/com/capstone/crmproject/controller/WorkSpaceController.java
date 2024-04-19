package com.capstone.crmproject.controller;

import com.capstone.crmproject.model.WorkSpace;
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
    @ResponseBody
    public ResponseEntity<String> addMember(@RequestBody AddMemberRequest member) {
        try{
            WorkSpace newWorkSpace = workSpaceService.addMember(member);
            String message = "Member added to workspace: " + newWorkSpace.getName();
            return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("{\"message\": \"add member failed\"}");

        }

    }
}
