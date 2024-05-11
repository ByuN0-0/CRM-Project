package com.capstone.crmproject.controller;

import com.capstone.crmproject.dto.RegisterUserDTO;
import com.capstone.crmproject.entity.UserEntity;
import com.capstone.crmproject.entity.WorkspaceEntity;
import com.capstone.crmproject.entity.WorkspaceMemberEntity;
import com.capstone.crmproject.request.UserRegisterRequest;
import com.capstone.crmproject.service.UserService;
import com.capstone.crmproject.service.WorkspaceMemberService;
import com.capstone.crmproject.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User", description = "유저 정보 관련")
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

    @Operation(summary = "회원가입", description = "회원가입")
    @Parameter(name = "registerUserDTO", description = "회원가입 정보")
    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity<String> registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        JSONObject responseData = new JSONObject();
        try {
            UserEntity newUser = userService.registerUser(registerUserDTO);
            WorkspaceEntity newWorkSpace = workspaceService.createWorkspace(registerUserDTO.getWorkspaceName(), newUser);
            WorkspaceMemberEntity newMember = workspaceMemberService.addMember(newWorkSpace.getWorkspaceId(), newUser.getUsername());

            responseData.put("userId", newUser.getUsername());
            responseData.put("workspaceId", newWorkSpace.getWorkspaceId());

            return ResponseEntity.ok().body(responseData.toString());
        } catch (Exception e) {

            responseData.put("error", e);
            return ResponseEntity.badRequest().body(responseData.toString());
        }
    }
}
