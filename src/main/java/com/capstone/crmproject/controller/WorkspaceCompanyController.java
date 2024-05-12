package com.capstone.crmproject.controller;

import com.capstone.crmproject.entity.WorkspaceCompanyEntity;
import com.capstone.crmproject.request.CompanyRequest;
import com.capstone.crmproject.service.WorkspaceCompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Tag(name = "WorkspaceCompany", description = "워크스페이스 내부 회사 정보 관련")
@Controller
public class WorkspaceCompanyController {
    private final WorkspaceCompanyService workspaceCompanyService;

    public WorkspaceCompanyController(WorkspaceCompanyService workspaceCompanyService) {
        this.workspaceCompanyService = workspaceCompanyService;
    }

    @Operation(summary = "회사 조회", description = "회사 조회")
    @Parameter(name = "workspaceId", description = "워크스페이스 ID, 회사 정보")
    @PostMapping("/api/workspace/{workspaceId}/company")
    @ResponseBody
    public ResponseEntity<String> findCompany(@PathVariable UUID workspaceId) {
        JSONObject responseData = new JSONObject();
        try {
            List<UUID> companiesIdList = new ArrayList<>();
            List<WorkspaceCompanyEntity> companies = workspaceCompanyService.getCompanyList(workspaceId);
            for (WorkspaceCompanyEntity company : companies) {
                companiesIdList.add(company.getCompanyId());
            }
            responseData.put("companies", companiesIdList);
            return ResponseEntity.ok().body(responseData.toString());
        } catch (Exception e) {
            responseData.put("error", e);
            return ResponseEntity.badRequest().body(responseData.toString());
        }
    }
}
