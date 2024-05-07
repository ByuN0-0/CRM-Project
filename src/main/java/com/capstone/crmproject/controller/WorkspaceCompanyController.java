package com.capstone.crmproject.controller;

import com.capstone.crmproject.entity.WorkspaceCompanyEntity;
import com.capstone.crmproject.request.CompanyRequest;
import com.capstone.crmproject.service.WorkspaceCompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;


@Controller
public class WorkspaceCompanyController {
    private final WorkspaceCompanyService workspaceCompanyService;

    public WorkspaceCompanyController(WorkspaceCompanyService workspaceCompanyService) {
        this.workspaceCompanyService = workspaceCompanyService;
    }

    @PostMapping("/api/workspace/{workspaceId}/company")
    @ResponseBody
    public ResponseEntity<String> findCompany(
            @PathVariable UUID workspaceId,
            @RequestBody CompanyRequest companyRequest
    ) {
        try {
            List<WorkspaceCompanyEntity> companies = workspaceCompanyService.getCompanyList(workspaceId);
            String message = "Company list: " + companies;
            return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"find company failed\"}");
        }
    }
}
