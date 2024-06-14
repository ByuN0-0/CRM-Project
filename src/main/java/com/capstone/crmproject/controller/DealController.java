package com.capstone.crmproject.controller;

import com.capstone.crmproject.dto.DealDTO;
import com.capstone.crmproject.dto.DealSearchDTO;
import com.capstone.crmproject.entity.DealAttributeEntity;
import com.capstone.crmproject.entity.DealEntity;
import com.capstone.crmproject.entity.DealValueEntity;
import com.capstone.crmproject.service.CompanyService;
import com.capstone.crmproject.service.DealService;
import com.capstone.crmproject.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.criteria.Predicate;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Tag(name = "Deal", description = "딜 정보 관련")
@Controller
public class DealController {
    private final DealService dealService;
    private final WorkspaceService workspaceService;
    private final CompanyService companyService;

    public DealController(DealService dealService, WorkspaceService workspaceService, CompanyService companyService) {
        this.dealService = dealService;
        this.workspaceService = workspaceService;
        this.companyService = companyService;

    }

    @Operation(summary = "딜 추가", description = "딜 추가")
    @PostMapping("/api/workspaces/{workspaceId}/deals")
    @ResponseBody
    public ResponseEntity<String> addDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId
    ) {
        if (!workspaceService.isMember(workspaceId, auth.getUsername()))
            return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");

        DealEntity newDeal = dealService.addDealEntity(workspaceId);
        JSONObject responseData = new JSONObject();
        responseData.put("dealId", newDeal.getDealId());
        return ResponseEntity.ok().body(responseData.toString());
    }

    @Operation(summary = "딜 속성 조회", description = "딜 속성 조회")
    @GetMapping("/api/workspaces/{workspaceId}/deals/attributes")
    @ResponseBody
    public ResponseEntity<String> getDealAttribute(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId
    ) {
        JSONObject responseData = new JSONObject();
        if (!workspaceService.isMember(workspaceId, auth.getUsername())) {
            responseData.put("error", "User is not a member of this workspace");
            return ResponseEntity.badRequest().body(responseData.toString());
        }
        List<DealAttributeEntity> attributeList = dealService.getDealAttributeList(workspaceId);
        JSONArray attributeArray = new JSONArray();
        for (DealAttributeEntity attribute : attributeList) {
            JSONObject attributeObject = new JSONObject();
            attributeObject.put("attributeId", attribute.getAttributeId());
            attributeObject.put("attributeName", attribute.getAttributeName());
            attributeArray.put(attributeObject);
        }
        responseData.put("attributeList", attributeArray);
        return ResponseEntity.ok().body(responseData.toString());
    }

    @Operation(summary = "딜 value 수정", description = "딜 수정")
    @PutMapping("/api/workspaces/{workspaceId}/deals/{dealId}/attributes/{attributeId}")
    @ResponseBody
    public ResponseEntity<String> updateDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId,
            @PathVariable UUID dealId,
            @PathVariable UUID attributeId,
            @RequestBody DealDTO dealDTO
    ) {
        if (!workspaceService.isMember(workspaceId, auth.getUsername()))
            return ResponseEntity.badRequest().body("{\"error\": \"authentication error\"}");

        DealValueEntity dealValue = dealService.updateDealValue(dealId, attributeId, dealDTO.getValue());
        JSONObject responseData = new JSONObject();
        responseData.put("dealId", dealValue.getDeal().getDealId().toString());
        responseData.put("attributeId", dealValue.getAttribute().getAttributeId().toString());
        responseData.put("value", dealValue.getValue());
        return ResponseEntity.ok().body(responseData.toString());
    }

    @Operation(summary = "딜 조회",
            description =   "sortProperty : 정렬 속성(createdDate, updatedDate)," +
                            " sortDirection : 정렬 방향(ASC,DESC)," +
                            " createdAfter : ~부터," +
                            " createdBefore : ~까지," +
                            " filterProperty : 보여줄attribute" +
                            " 기본값: createdDate, ASC, 2000-01-01, 2030-12-31, 모든 attribute")
    @GetMapping("/api/workspaces/{workspaceId}/deals")
    @ResponseBody
    public ResponseEntity<String> getDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId
    ) {
        JSONObject responseData = new JSONObject();
        if (!workspaceService.isMember(workspaceId, auth.getUsername())) {
            responseData.put("error", "User is not a member of this workspace");
            return ResponseEntity.badRequest().body(responseData.toString());
        }

        String sortProperty = null; // = dealSearchDTO.getSortProperty();
        String sortDirection= null; // = dealSearchDTO.getSortDirection();
        LocalDateTime createdAfter= null; // = dealSearchDTO.getCreatedAfter();
        LocalDateTime createdBefore= null; // = dealSearchDTO.getCreatedBefore();
        List<String> filterProperty= null; // = dealSearchDTO.getFilterProperty();

        if (sortProperty == null || sortProperty.isEmpty()) {
            sortProperty = "createdDate"; // 기본 정렬 속성 설정
        }
        if (sortDirection == null || sortDirection.isEmpty()) {
            sortDirection = "ASC"; // 기본 정렬 방향 설정
        }
        if (createdAfter == null) {
            createdAfter = LocalDateTime.of(2000, 1, 1, 0, 0, 0);
        }
        if (createdBefore == null) {
            createdBefore = LocalDateTime.of(2030, 12, 31, 23, 59, 59);
        }
        if (filterProperty == null || filterProperty.isEmpty()) {
            filterProperty = workspaceService.getWorkspace(workspaceId).getDealAttributes().stream().map(DealAttributeEntity::getAttributeName).toList();
        }

        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC, sortProperty);

        List<DealEntity> dealList = dealService.getFilteredAndSortedDealList(workspaceId, createdAfter, createdBefore, sort);

        JSONArray dealArray = new JSONArray();
        for (DealEntity deal : dealList) {
            JSONObject dealObject = new JSONObject();
            dealObject.put("dealId", deal.getDealId());
            dealObject.put("createdDate", deal.getCreatedDate());
            dealObject.put("updatedDate", deal.getUpdatedDate());
            List<String> finalFilterProperty = filterProperty;
            List<DealValueEntity> filteredDealValues = deal.getDealValues().stream()
                    // 여기에 원하는 조건을 적용하여 필터링
                    .filter(dealValue -> {
                        for (String property : finalFilterProperty) {
                            if (dealValue.getAttribute().getAttributeName().equals(property)) {
                                return true;
                            }
                        }
                        return false;
                    }).toList();
            filteredDealValues.forEach(dealValue -> {
                dealObject.put(dealValue.getAttribute().getAttributeId().toString(), dealValue.getValue());
            });
            dealArray.put(dealObject);
        }
        responseData.put("dealList", dealArray);
        return ResponseEntity.ok().body(responseData.toString());
    }

    @Operation(summary = "딜 삭제", description = "딜 삭제")
    @DeleteMapping("/api/workspaces/{workspaceId}/deals/{dealId}")
    @ResponseBody
    public ResponseEntity<String> deleteDeal(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId,
            @PathVariable UUID dealId
    ) {
        JSONObject responseData = new JSONObject();
        if (!workspaceService.isMember(workspaceId, auth.getUsername())) {
            responseData.put("error", "User is not a member of this workspace");
            return ResponseEntity.badRequest().body(responseData.toString());
        }
        dealService.deleteDealEntity(dealId);
        responseData.put("message", "Deal deleted");
        return ResponseEntity.ok().body(responseData.toString());
    }

    @Operation(summary = "딜 속성 삭제", description = "딜 속성 삭제")
    @DeleteMapping("/api/workspaces/{workspaceId}/deals/attributes/{attributeId}")
    @ResponseBody
    public ResponseEntity<String> deleteDealAttribute(
            @AuthenticationPrincipal UserDetails auth,
            @PathVariable UUID workspaceId,
            @PathVariable UUID attributeId
    ) {
        JSONObject responseData = new JSONObject();
        if (!workspaceService.isMember(workspaceId, auth.getUsername())) {
            responseData.put("error", "User is not a member of this workspace");
            return ResponseEntity.badRequest().body(responseData.toString());
        }
        dealService.deleteDealAttribute(attributeId);
        responseData.put("message", "Attribute deleted");
        return ResponseEntity.ok().body(responseData.toString());
    }
}
