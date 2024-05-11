package com.capstone.crmproject.controller;

import com.capstone.crmproject.entity.WorkspaceMemberEntity;
import com.capstone.crmproject.security.CustomUserDetails;
import com.capstone.crmproject.entity.WorkspaceEntity;
import com.capstone.crmproject.service.UserService;
import com.capstone.crmproject.service.WorkspaceMemberService;
import com.capstone.crmproject.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@Tag(name = "Workspace", description = "워크스페이스 정보 관련")
@Controller
public class WorkspaceController {
    private final WorkspaceService workspaceService;
    private final WorkspaceMemberService workspaceMemberService;
    private final UserService userService;

    public WorkspaceController(WorkspaceService workspaceService, UserService userService, WorkspaceMemberService workspaceMemberService) {
        this.workspaceService = workspaceService;
        this.workspaceMemberService = workspaceMemberService;
        this.userService = userService;
    }


    @Operation(summary = "워크스페이스 정보", description = "워크스페이스 정보 조회")
    @Parameter(name = "workspaceId", description = "워크스페이스 ID")
    @PostMapping("/api/workspace/{workspaceId}")
    @ResponseBody
    public ResponseEntity<String> getWorkspace(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable UUID workspaceId) {
        JSONObject responseData = new JSONObject();
        try {
            if(workspaceMemberService.isMember(workspaceId, auth.getUsername())) return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");
            WorkspaceEntity workspace = workspaceService.getWorkspace(workspaceId);
            responseData.put("workspaceId", workspace.getWorkspaceId());
            responseData.put("workspaceName", workspace.getName());
            responseData.put("workspaceOwnerId", workspace.getOwnerId());
            return ResponseEntity.ok().body(responseData.toString());

        } catch (Exception e) {
            responseData.put("error", e);
            return ResponseEntity.badRequest().body(responseData.toString());
        }
    }
}
