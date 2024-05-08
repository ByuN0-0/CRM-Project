package com.capstone.crmproject.controller;

import com.capstone.crmproject.dto.DealDTO;
import com.capstone.crmproject.entity.DealEntity;
import com.capstone.crmproject.service.DealService;
import com.capstone.crmproject.service.WorkspaceMemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@ResponseBody
public class DealController {
    private final DealService dealService;
    private final WorkspaceMemberService workspaceMemberService;
    public DealController(DealService dealService, WorkspaceMemberService workspaceMemberService){
        this.dealService = dealService;
        this.workspaceMemberService = workspaceMemberService;

    }
    @PostMapping("/api/workspace/{workspaceId}/deal/add")
    public ResponseEntity<String> addDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId,
            DealDTO dealDTO
    ) {
        // 1차로 유저가 workspace를 수정할 권한이 있는지
        if (!workspaceMemberService.isMember(workspaceId, auth.getUsername())) return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");
        DealEntity newDeal = dealService.addDealEntity(dealDTO);
        LocalDateTime date = newDeal.getCreateDate();
        return ResponseEntity.ok("{" + date.toString() + "}");
    }

    @PostMapping("/api/workspace/{workspaceId}/deal/{dealId}/update")
    public ResponseEntity<String> updateDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId,
            @PathVariable UUID dealId,
            DealDTO dealDTO
    ) {
        // 1차로 유저가 workspace를 수정할 권한이 있는지
        if (!workspaceMemberService.isMember(workspaceId, auth.getUsername())) return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");

        DealEntity newDeal = dealService.updateDealEntity(dealDTO);
        LocalDateTime date = newDeal.getCreateDate();
        return ResponseEntity.ok("{" + date.toString() + "}");
    }
}
