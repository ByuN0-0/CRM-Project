package com.capstone.crmproject.controller;

import com.capstone.crmproject.dto.DealDTO;
import com.capstone.crmproject.entity.CompanyEntity;
import com.capstone.crmproject.entity.DealAttribute;
import com.capstone.crmproject.entity.DealEntity;
import com.capstone.crmproject.entity.deal.DealValue;
import com.capstone.crmproject.service.CompanyService;
import com.capstone.crmproject.service.DealService;
import com.capstone.crmproject.service.WorkspaceMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "Deal", description = "딜 정보 관련")
@Controller
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
    @ResponseBody
    public ResponseEntity<String> addDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId
    ) {
        if (workspaceMemberService.isMember(workspaceId, auth.getUsername()))
            return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");

        DealEntity newDeal = dealService.addDealEntity(workspaceId);
        JSONObject responseData = new JSONObject();
        responseData.put("dealId", newDeal.getDealId());
        return ResponseEntity.ok().body(responseData.toString());
    }

    @Operation(summary = "딜 속성 조회", description = "딜 속성 조회")
    @PostMapping("/api/workspace/{workspaceId}/deal/attribute")
    @ResponseBody
    public ResponseEntity<String> getDealAttribute(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId
    ) {
        JSONObject responseData = new JSONObject();
        if (workspaceMemberService.isMember(workspaceId, auth.getUsername())) {
            responseData.put("error", "User is not a member of this workspace");
            return ResponseEntity.badRequest().body(responseData.toString());
        }
        List<DealAttribute> attributeList = dealService.getDealAttributeList(workspaceId);
        JSONArray attributeArray = new JSONArray();
        for (DealAttribute attribute : attributeList) {
            JSONObject attributeObject = new JSONObject();
            attributeObject.put("attributeId", attribute.getAttributeId());
            attributeObject.put("attributeName", attribute.getAttributeName());
            attributeArray.put(attributeObject);
        }
        responseData.put("attributeList", attributeArray);
        return ResponseEntity.ok().body(responseData.toString());
    }

    @Operation(summary = "딜 value 수정", description = "딜 수정")
    @PostMapping("/api/workspace/{workspaceId}/deal/{dealId}/attribute/{attributeId}/update")
    @ResponseBody
    public ResponseEntity<String> updateDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId,
            @PathVariable UUID dealId,
            @PathVariable UUID attributeId,
            @RequestBody DealDTO dealDTO
    ) {
        if (workspaceMemberService.isMember(workspaceId, auth.getUsername()))
            return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");

        DealValue dealValue = dealService.updateDealValue(dealId, attributeId, dealDTO.getValue());
        JSONObject responseData = new JSONObject();
        responseData.put("dealId", dealValue.getDealId());

        return ResponseEntity.ok().body(responseData.toString());
    }

    @Operation(summary = "딜 조회", description = "딜 조회")
    @PostMapping("/api/workspace/{workspaceId}/deal/")
    @ResponseBody
    public ResponseEntity<String> getDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId
    ) {
        JSONObject responseData = new JSONObject();
        if (workspaceMemberService.isMember(workspaceId, auth.getUsername())) {
            responseData.put("error", "User is not a member of this workspace");
            return ResponseEntity.badRequest().body(responseData.toString());
        }
        List<DealEntity> dealList = dealService.getDealList(workspaceId);
        JSONArray dealArray = new JSONArray();
        for (DealEntity deal : dealList) {
            JSONObject dealObject = new JSONObject();
            dealObject.put("dealId", deal.getDealId());
            dealObject.put("createdDate", deal.getCreatedDate());
            dealObject.put("updatedDate", deal.getUpdatedDate());
            dealArray.put(dealObject);
        }
        responseData.put("dealList", dealArray);
        return ResponseEntity.ok().body(responseData.toString());
    }

    @Operation(summary = "딜 삭제", description = "딜 삭제")
    @PostMapping("/api/workspace/{workspaceId}/deal/{dealId}/delete")
    @ResponseBody
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
        dealService.deleteDealEntity(dealId);
        responseData.put("message", "Deal deleted");
        return ResponseEntity.ok().body(responseData.toString());
    }
}
