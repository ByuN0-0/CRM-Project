package com.capstone.crmproject.controller;

import com.capstone.crmproject.dto.RegisterUserDTO;
import com.capstone.crmproject.entity.UserEntity;
import com.capstone.crmproject.entity.WorkspaceEntity;
import com.capstone.crmproject.entity.WorkspaceMemberEntity;
import com.capstone.crmproject.request.UserRegisterRequest;
import com.capstone.crmproject.service.UserService;
import com.capstone.crmproject.service.WorkspaceMemberService;
import com.capstone.crmproject.service.WorkspaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private final UserService userService;
    private final WorkspaceService workspaceService;
    private final WorkspaceMemberService workspaceMemberService;

    public UserController(UserService userService, WorkspaceService workspaceService, WorkspaceMemberService workspaceMemberService) {
        this.userService = userService;
        this.workspaceService = workspaceService;
        this.workspaceMemberService = workspaceMemberService;
    }


    @GetMapping("/register")
    public String join() {
        return "join";
    }

    @PostMapping("/api/register")
    @ResponseBody
    public String registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        try {
            UserEntity newUser = userService.registerUser(registerUserDTO);
            WorkspaceEntity newWorkSpace = workspaceService.createWorkspace(registerUserDTO.getWorkspaceName(), newUser);
            WorkspaceMemberEntity newMember = workspaceMemberService.addMember(newWorkSpace.getWorkspaceId(), newUser.getUsername());
            return "User registered with ID: " + newUser.getUsername() + ", Workspace created with Name: " + newWorkSpace.getName() + ", Member added to workspace: " + newMember.getMemberId();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
