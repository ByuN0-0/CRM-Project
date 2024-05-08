package com.capstone.crmproject.controller;

import com.capstone.crmproject.dto.DealDTO;
import com.capstone.crmproject.entity.DealEntity;
import com.capstone.crmproject.service.DealService;
import com.capstone.crmproject.service.WorkspaceMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "Deal", description = "딜 정보 관련")
@Controller
@ResponseBody
public class DealController {
    private final DealService dealService;
    private final WorkspaceMemberService workspaceMemberService;
    public DealController(DealService dealService, WorkspaceMemberService workspaceMemberService){
        this.dealService = dealService;
        this.workspaceMemberService = workspaceMemberService;

    }

    @Operation(summary = "딜 추가", description = "딜 추가")
    @Parameter(name = "workspaceId, dealDTO", description = "워크스페이스 ID, 딜 정보")
    @PostMapping("/api/workspace/{workspaceId}/deal/add")
    public ResponseEntity<String> addDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId,
            DealDTO dealDTO
    ) {
        if (workspaceMemberService.isMember(workspaceId, auth.getUsername())) return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");
        DealEntity newDeal = dealService.addDealEntity(dealDTO);
        LocalDateTime date = newDeal.getCreateDate();
        return ResponseEntity.ok("{" + date.toString() + "}");
    }

    @Operation(summary = "딜 수정", description = "딜 수정")
    @Parameter(name = "workspaceId, dealId, dealDTO", description = "워크스페이스 ID, 딜 ID, 딜 정보")
    @PostMapping("/api/workspace/{workspaceId}/deal/{dealId}/update")
    public ResponseEntity<String> updateDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId,
            @PathVariable UUID dealId,
            DealDTO dealDTO
    ) {
        if (workspaceMemberService.isMember(workspaceId, auth.getUsername())) return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");

        DealEntity newDeal = dealService.updateDealEntity(dealDTO);
        LocalDateTime date = newDeal.getCreateDate();
        return ResponseEntity.ok("{" + date.toString() + "}");
    }

    @Operation(summary = "딜 조회", description = "딜 조회")
    @Parameter(name = "workspaceId, dealId", description = "워크스페이스 ID, 딜 ID")
    @PostMapping("/api/workspace/{workspaceId}/deal/")
    public ResponseEntity<String> getDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId
    ) {
        if (workspaceMemberService.isMember(workspaceId, auth.getUsername())) return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");

        List<DealEntity> dealList = dealService.getDealList(workspaceId);
        return ResponseEntity.ok("{" + dealList.toString() + "}");
    }

    @Operation(summary = "딜 삭제", description = "딜 삭제")
    @Parameter(name = "workspaceId, dealId", description = "워크스페이스 ID, 딜 ID")
    @PostMapping("/api/workspace/{workspaceId}/deal/{dealId}/delete")
    public ResponseEntity<String> deleteDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId,
            @PathVariable UUID dealId
    ) {
        if (workspaceMemberService.isMember(workspaceId, auth.getUsername())) return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");

        dealService.deleteDealEntity(dealId);
        return ResponseEntity.ok("{\"message\": \"delete deal success\"}");
    }
}
