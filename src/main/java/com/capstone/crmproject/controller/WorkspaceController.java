package com.capstone.crmproject.controller;

import com.capstone.crmproject.security.CustomUserDetails;
import com.capstone.crmproject.entity.WorkspaceEntity;
import com.capstone.crmproject.service.UserService;
import com.capstone.crmproject.service.WorkspaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@Controller
public class WorkspaceController {
    private final WorkspaceService workspaceService;
    private final UserService userService;

    public WorkspaceController(WorkspaceService workspaceService, UserService userService) {
        this.workspaceService = workspaceService;
        this.userService = userService;
    }

    @PostMapping("/api/workspace/{workspaceId}")
    @ResponseBody
    public ResponseEntity<String> getWorkspace(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable UUID workspaceId) {
        try {
            WorkspaceEntity workspace = workspaceService.getWorkspace(workspaceId);
            return ResponseEntity.ok().body(workspace.getName()+ " - " + workspace.getOwnerId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"get workspace failed\"}");
        }
    }
}
