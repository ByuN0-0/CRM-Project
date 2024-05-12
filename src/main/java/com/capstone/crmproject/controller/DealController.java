package com.capstone.crmproject.controller;

import com.capstone.crmproject.dto.DealDTO;
import com.capstone.crmproject.entity.CompanyEntity;
import com.capstone.crmproject.entity.DealEntity;
import com.capstone.crmproject.service.CompanyService;
import com.capstone.crmproject.service.DealService;
import com.capstone.crmproject.service.WorkspaceMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
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
    private final CompanyService companyService;

    public DealController(DealService dealService, WorkspaceMemberService workspaceMemberService, CompanyService companyService) {
        this.dealService = dealService;
        this.workspaceMemberService = workspaceMemberService;
        this.companyService = companyService;

    }

    @Operation(summary = "딜 추가", description = "딜 추가")
    @PostMapping("/api/workspace/{workspaceId}/deal/add")
    public ResponseEntity<String> addDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId,
            DealDTO dealDTO
    ) {
        if (workspaceMemberService.isMember(workspaceId, auth.getUsername()))
            return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");
        DealEntity newDeal = dealService.addDealEntity(dealDTO);
        LocalDateTime date = newDeal.getCreateDate();
        return ResponseEntity.ok("{" + date.toString() + "}");
    }

    @Operation(summary = "딜 수정", description = "딜 수정")
    @PostMapping("/api/workspace/{workspaceId}/deal/{dealId}/update")
    public ResponseEntity<String> updateDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId,
            @PathVariable UUID dealId,
            DealDTO dealDTO
    ) {
        if (workspaceMemberService.isMember(workspaceId, auth.getUsername()))
            return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");

        DealEntity newDeal = dealService.updateDealEntity(dealDTO);
        LocalDateTime date = newDeal.getCreateDate();
        return ResponseEntity.ok("{" + date.toString() + "}");
    }

    @Operation(summary = "딜 조회", description = "딜 조회")
    @PostMapping("/api/workspace/{workspaceId}/deal/")
    public ResponseEntity<String> getDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId
    ) {
        JSONObject responseData = new JSONObject();
        if (workspaceMemberService.isMember(workspaceId, auth.getUsername())) {
            responseData.put("error", "User is not a member of this workspace");
            return ResponseEntity.badRequest().body(responseData.toString());
        }
        try{
            List<DealEntity> dealEntityList = dealService.getDealList(workspaceId);
            JSONArray dealList = new JSONArray();
            for (DealEntity dealEntity : dealEntityList) {
                JSONObject deal = new JSONObject();
                JSONObject company = new JSONObject();
                CompanyEntity companyEntity = companyService.getCompany(dealEntity.getCompanyId());

                company.put("companyId", dealEntity.getCompanyId());
                company.put("companyName", companyEntity.getCompanyName());

                deal.put("dealId", dealEntity.getId());
                deal.put("workspaceId", dealEntity.getWorkspaceId());
                deal.put("company", company);
                deal.put("Memo", dealEntity.getMemo());
                deal.put("Email", dealEntity.getEmail());
                deal.put("investmentRound", dealEntity.getInvestmentRound());
                deal.put("createDate", dealEntity.getCreateDate());
                deal.put("updateDate", dealEntity.getUpdateDate());
                dealList.put(deal);
            }
            responseData.put("dealList", dealList);

            return ResponseEntity.ok().body(responseData.toString());
        } catch (Exception e) {
            responseData.put("error", e);
            return ResponseEntity.badRequest().body(responseData.toString());
        }
    }

    @Operation(summary = "딜 삭제", description = "딜 삭제")
    @PostMapping("/api/workspace/{workspaceId}/deal/{dealId}/delete")
    public ResponseEntity<String> deleteDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId,
            @PathVariable UUID dealId
    ) {
        JSONObject responseData = new JSONObject();
        if (workspaceMemberService.isMember(workspaceId, auth.getUsername())) {
            responseData.put("error", "User is not a member of this workspace");
            return ResponseEntity.badRequest().body(responseData.toString());
        }
        try {
            dealService.deleteDealEntity(dealId);
            responseData.put("message", "delete deal success");
            return ResponseEntity.ok().body(responseData.toString());
        } catch (Exception e) {
            responseData.put("error", e);
            return ResponseEntity.badRequest().body(responseData.toString());
        }
    }
}
