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
    public String modifyCompanyInfo(
            @AuthenticationPrincipal CustomUserDetails auth,
            @PathVariable UUID companyId,
            @RequestBody CompanyDTO companyDTO
    ) {
        //UUID aceId = auth.getUserId();
        var company = companyService.getCompany(companyId);
        if (company == null) {
            throw new EntityNotFoundException("Company not found");
        } else {
            try {
                CompanyEntity newCompany = companyService.updateCompany(companyDTO);
                return "Company update to workspace: " + newCompany.getCompanyName();
            } catch (Exception e) {
                return "update company failed";
            }
        }
    }


    @Operation(summary = "회사 정보 추가", description = "회사 정보 추가")
    @Parameter(name = "companyDTO", description = "회사 정보")
    @PostMapping("/api/company/add")
    @ResponseBody
    public String addCompany(
            @AuthenticationPrincipal CustomUserDetails auth,
            @RequestBody CompanyDTO companyDTO
    ) {

        //UUID id = auth.getWorkspaceId();

        try {
            CompanyEntity newCompany = companyService.insertCompany(companyDTO);
            return "Company added to workspace: " + newCompany.getCompanyName() + " - " + newCompany.getCompanyId();
        } catch (Exception e) {
            return "add company failed";
        }
    }

    @Operation(summary = "회사 정보 조회", description = "회사 정보 조회")
    @Parameter(name = "companyId", description = "회사 ID")
    @PostMapping("/api/company/{companyId}")
    @ResponseBody
    public ResponseEntity<String> getCompany(@PathVariable UUID companyId){
        try {
            CompanyEntity company = companyService.getCompany(companyId);
            String message = "Company: " + company.getCompanyName() + " - " + company.getCompanyId();
            return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"message\": \"find company failed\"}");
        }
    }
}
