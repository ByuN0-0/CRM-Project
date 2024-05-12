package com.capstone.crmproject.controller;

import com.capstone.crmproject.dto.CompanyDTO;
import com.capstone.crmproject.security.CustomUserDetails;
import com.capstone.crmproject.entity.CompanyEntity;
import com.capstone.crmproject.request.CompanyRequest;
import com.capstone.crmproject.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;


@Tag(name = "Company", description = "회사 정보 관련")
@Controller
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Operation(summary = "회사 정보 수정", description = "회사 정보 수정")
    @Parameter(name = "companyId", description = "회사 ID, 회사 정보")
    @PostMapping("/api/company/{companyId}/modify")
    @ResponseBody
    public ResponseEntity<String> modifyCompanyInfo(
            @AuthenticationPrincipal CustomUserDetails auth,
            @PathVariable UUID companyId,
            @RequestBody CompanyDTO companyDTO
    ) {
        //UUID aceId = auth.getUserId();
        CompanyEntity companyEntity;
        JSONObject responseData = new JSONObject();
        try {
            companyEntity = companyService.updateCompany(companyDTO);
        } catch (Exception e) {
            responseData.put("error", e);
            return ResponseEntity.badRequest().body(responseData.toString());
        }
        responseData.put("companyId", companyEntity.getCompanyId());
        responseData.put("companyName", companyEntity.getCompanyName());

        return ResponseEntity.ok().body(responseData.toString());

    }


    @Operation(summary = "회사 정보 추가", description = "회사 정보 추가")
    @PostMapping("/api/company/add")
    @ResponseBody
    public ResponseEntity<String> addCompany(
            @AuthenticationPrincipal CustomUserDetails auth,
            @RequestBody CompanyDTO companyDTO
    ) {
        //UUID id = auth.getWorkspaceId();
        CompanyEntity newCompany;
        JSONObject responseData = new JSONObject();
        try {
            newCompany = companyService.insertCompany(companyDTO);
        } catch (Exception e) {
            responseData.put("error", e);
            return ResponseEntity.badRequest().body(responseData.toString());
        }
        JSONObject companyData = new JSONObject();
        companyData.put("companyId", newCompany.getCompanyId());
        companyData.put("companyName", newCompany.getCompanyName());
        responseData.put("company", companyData);

        return ResponseEntity.ok().body(responseData.toString());
    }

    @Operation(summary = "회사 정보 조회", description = "회사 정보 조회")
    @Parameter(name = "companyId", description = "회사 ID")
    @PostMapping("/api/company/{companyId}")
    @ResponseBody
    public ResponseEntity<String> getCompany(@PathVariable UUID companyId) {
        CompanyEntity companyEntity;
        JSONObject responseData = new JSONObject();
        try {
            companyEntity = companyService.getCompany(companyId);
        } catch (Exception e) {
            responseData.put("error", e);
            return ResponseEntity.badRequest().body(responseData.toString());
        }

        JSONObject companyData = new JSONObject();
        companyData.put("companyId", companyEntity.getCompanyId());
        companyData.put("companyName", companyEntity.getCompanyName());
        responseData.put("company", companyData);

        return ResponseEntity.ok().body(responseData.toString());
    }
}
