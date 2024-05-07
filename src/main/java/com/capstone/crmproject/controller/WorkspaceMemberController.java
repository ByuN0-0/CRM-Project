package com.capstone.crmproject.controller;

import com.capstone.crmproject.dto.CustomUserDetails;
import com.capstone.crmproject.dto.WorkspaceDTO;
import com.capstone.crmproject.entity.WorkspaceEntity;
import com.capstone.crmproject.entity.WorkspaceMemberEntity;
import com.capstone.crmproject.request.WorkspaceMemberRequest;
import com.capstone.crmproject.request.WorkspaceRequest;
import com.capstone.crmproject.service.WorkspaceMemberService;

import com.capstone.crmproject.service.WorkspaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Controller
public class WorkspaceMemberController {
    private final WorkspaceMemberService workspaceMemberService;
    private final WorkspaceService workspaceService;
    public WorkspaceMemberController(WorkspaceMemberService workspaceMemberService, WorkspaceService workspaceService) {
        this.workspaceMemberService = workspaceMemberService;
        this.workspaceService = workspaceService;
    }
    @PostMapping("/api/workspace/{workspaceId}/member")
    @ResponseBody
    public ResponseEntity<String> addMember(@RequestBody WorkspaceDTO workspaceDTO) {
        UUID workspaceId = workspaceDTO.getWorkspaceId();
        String memberId = workspaceDTO.getMemberId();
        try {
            WorkspaceMemberEntity workspaceMemberEntity = workspaceMemberService.addMember(workspaceId, memberId);
            String message = "Member added to workspace: " + workspaceMemberEntity.getMemberId();
            return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"add member failed\"}");
        }
    }

    @PostMapping("/api/workspace/member")
    @ResponseBody
    public String getMemberList(@RequestBody WorkspaceMemberRequest workspaceMemberRequest) {
        try {
            List<WorkspaceMemberEntity> workspaceMemberEntityList = workspaceMemberService.getMemberList(workspaceMemberRequest.getWorkspaceId());
            return workspaceMemberEntityList.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/api/my-workspace")
    @ResponseBody
    public String getMyWorkspace(@AuthenticationPrincipal UserDetails auth) {
        try {
            List<WorkspaceMemberEntity> workspaceMemberEntityList = workspaceMemberService.getWorkspaceList(auth.getUsername());
            String returnString = "";
            for (WorkspaceMemberEntity workspaceMemberEntity : workspaceMemberEntityList) {
                String workspaceName = workspaceService.getWorkspace(workspaceMemberEntity.getWorkspaceId()).getName();
                UUID workspaceId = workspaceMemberEntity.getWorkspaceId();
                returnString += workspaceId.toString()+ " : " +workspaceName  + "\n";

                log.info(workspaceId.toString());
            }
            return returnString;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
