package com.capstone.crmproject.request;

import lombok.Getter;

@Getter
public class CompanyRequest {
    private int workspaceId;
    private int companyId;
    private String owner;
    private String companyName;
    private String newCompanyName;
}
