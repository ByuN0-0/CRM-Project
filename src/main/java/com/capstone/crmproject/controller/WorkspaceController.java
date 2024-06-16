package com.capstone.crmproject.controller;

import com.capstone.crmproject.dto.WorkspaceDTO;
import com.capstone.crmproject.entity.UserEntity;
import com.capstone.crmproject.security.CustomUserDetails;
import com.capstone.crmproject.entity.WorkspaceEntity;
import com.capstone.crmproject.service.UserService;
import com.capstone.crmproject.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Workspace", description = "워크스페이스 정보 관련")
@Controller
public class WorkspaceController {
    private final WorkspaceService workspaceService;
    private final UserService userService;

    public WorkspaceController(WorkspaceService workspaceService, UserService userService) {
        this.workspaceService = workspaceService;
        this.userService = userService;
    }

    @Operation(summary = "워크스페이스 정보", description = "워크스페이스 정보 조회")
    @Parameter(name = "workspaceId", description = "워크스페이스 ID")
    @GetMapping("/api/workspaces/{workspaceId}")
    @ResponseBody
    public ResponseEntity<String> getWorkspace(@AuthenticationPrincipal CustomUserDetails auth, @PathVariable UUID workspaceId) {
        JSONObject responseData = new JSONObject();
        try {
            if (workspaceService.isMember(workspaceId, auth.getUsername()))
                return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");
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

    @Operation(summary = "멤버 추가", description = "멤버 추가")
    @Parameter(name = "workspaceId, memberId", description = "워크스페이스 ID, 멤버 정보")
    @PostMapping("/api/workspaces/{workspaceId}/member")
    @ResponseBody
    public ResponseEntity<String> addMember(@PathVariable UUID workspaceId, @RequestBody WorkspaceDTO workspaceDTO) {
        String memberId = workspaceDTO.getMemberId();
        try {
            WorkspaceEntity workspace = workspaceService.getWorkspace(workspaceId);
            workspaceService.addMember(workspaceId, memberId);
            String message = "Member added to workspace: " + workspace.getName();
            return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"add member failed\"}");
        }
    }

    @Operation(summary = "멤버 조회", description = "멤버 조회")
    @Parameter(name = "workspaceId", description = "워크스페이스 ID")
    @GetMapping("/api/workspaces/{workspaceId}/member")
    @ResponseBody
    public ResponseEntity<String> getMemberList(@AuthenticationPrincipal UserDetails auth, @PathVariable UUID workspaceId) {
        JSONObject responseData = new JSONObject();
        JSONArray members = new JSONArray();
        try {
            if (workspaceService.isMember(workspaceId, auth.getUsername()))
                return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");
            WorkspaceEntity workspace = workspaceService.getWorkspace(workspaceId);
            List<UserEntity> memberList = workspace.getUsers();
            for (UserEntity member : memberList) {
                JSONObject memberObject = new JSONObject();
                memberObject.put("memberId", member.getUsername());
                members.put(memberObject);
            }
            responseData.put("members", members);
            return ResponseEntity.ok().body(responseData.toString());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"get member failed\"}");
        }
    }

    @Operation(summary = "내 워크스페이스 목록 조회", description = "내 워크스페이스 목록 조회")
    @GetMapping("/api/workspaces")
    @ResponseBody
    public ResponseEntity<String> getMyWorkspaceList(@AuthenticationPrincipal UserDetails auth) {
        JSONObject responseData = new JSONObject();
        try {
            List<WorkspaceEntity> workspaceList = userService.getUser(auth.getUsername()).getWorkspaces();
            JSONArray workspaces = new JSONArray();
            for (WorkspaceEntity workspace : workspaceList) {
                JSONObject workspaceObject = new JSONObject();
                workspaceObject.put("workspaceId", workspace.getWorkspaceId());
                workspaceObject.put("workspaceName", workspace.getName());
                workspaces.put(workspaceObject);
            }
            responseData.put("workspaces", workspaces);
            return ResponseEntity.ok().body(responseData.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"get workspace failed\"}");
        }
        /*
        JSONObject responseData = new JSONObject();
        try {
            List<WorkspaceMember> workspaceMemberEntityList = workspaceMemberService.getWorkspaceList(auth.getUsername());
            JSONArray workspaceList = new JSONArray();
            for (WorkspaceMember workspaceMemberEntity : workspaceMemberEntityList) {
                JSONObject workspace = new JSONObject();
                workspace.put("workspaceId", workspaceMemberEntity.getWorkspaceId());
                workspace.put("workspaceName", workspaceService.getWorkspace(workspaceMemberEntity.getWorkspaceId()).getName());
                workspaceList.put(workspace);
            }
            responseData.put("workspaces", workspaceList);
            return ResponseEntity.ok().body(responseData.toString());
        } catch (Exception e) {
            responseData.put("error", e);
            return ResponseEntity.badRequest().body(responseData.toString());
        }

         */
    }
}
