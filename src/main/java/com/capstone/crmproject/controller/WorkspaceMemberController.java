package com.capstone.crmproject.controller;

import com.capstone.crmproject.dto.WorkspaceDTO;
import com.capstone.crmproject.entity.WorkspaceMemberEntity;
import com.capstone.crmproject.request.WorkspaceMemberRequest;
import com.capstone.crmproject.service.WorkspaceMemberService;

import com.capstone.crmproject.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@Tag(name = "WorkspaceMember", description = "워크스페이스 멤버 정보 관련")
@Controller
public class WorkspaceMemberController {
    private final WorkspaceMemberService workspaceMemberService;
    private final WorkspaceService workspaceService;

    public WorkspaceMemberController(WorkspaceMemberService workspaceMemberService, WorkspaceService workspaceService) {
        this.workspaceMemberService = workspaceMemberService;
        this.workspaceService = workspaceService;
    }

    @Operation(summary = "멤버 추가", description = "멤버 추가")
    @Parameter(name = "workspaceId, memberId", description = "워크스페이스 ID, 멤버 정보")
    @PostMapping("/api/workspace/{workspaceId}/add-member")
    @ResponseBody
    public ResponseEntity<String> addMember(@PathVariable UUID workspaceId, @RequestBody WorkspaceDTO workspaceDTO) {
        String memberId = workspaceDTO.getMemberId();
        try {
            WorkspaceMemberEntity workspaceMemberEntity = workspaceMemberService.addMember(workspaceId, memberId);
            String message = "Member added to workspace: " + workspaceMemberEntity.getMemberId();
            return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"add member failed\"}");
        }
    }

    @Operation(summary = "멤버 조회", description = "멤버 조회")
    @Parameter(name = "workspaceId", description = "워크스페이스 ID")
    @PostMapping("/api/workspace/{workspaceId}/member")
    @ResponseBody
    public ResponseEntity<String> getMemberList(@AuthenticationPrincipal UserDetails auth, @PathVariable UUID workspaceId) {
        try {
            if (workspaceMemberService.isMember(workspaceId, auth.getUsername()))
                return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");
            List<WorkspaceMemberEntity> workspaceMemberEntityList = workspaceMemberService.getMemberList(workspaceId);
            StringBuilder returnString = new StringBuilder();
            for (WorkspaceMemberEntity workspaceMemberEntity : workspaceMemberEntityList) {
                returnString.append(workspaceMemberEntity.getMemberId()).append("\n");
            }
            return ResponseEntity.ok().body(returnString.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"get member failed\"}");
        }
    }

    @Operation(summary = "내 워크스페이스 목록 조회", description = "내 워크스페이스 목록 조회")
    @GetMapping("/api/my-workspace")
    @ResponseBody
    public ResponseEntity<String> getMyWorkspaceList(@AuthenticationPrincipal UserDetails auth) {
        try {
            List<WorkspaceMemberEntity> workspaceMemberEntityList = workspaceMemberService.getWorkspaceList(auth.getUsername());
            StringBuilder returnString = new StringBuilder();
            for (WorkspaceMemberEntity workspaceMemberEntity : workspaceMemberEntityList) {
                String workspaceName = workspaceService.getWorkspace(workspaceMemberEntity.getWorkspaceId()).getName();
                UUID workspaceId = workspaceMemberEntity.getWorkspaceId();
                returnString.append(workspaceId.toString()).append(" : ").append(workspaceName).append("\n");

                log.info(workspaceId.toString());
            }
            return ResponseEntity.ok().body(returnString.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"get workspace failed\"}");
        }
    }

}
