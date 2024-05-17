package com.capstone.crmproject.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

import java.sql.Date;
import java.util.UUID;

@Getter
public class DealDTO {
    private UUID workspaceId;
    private UUID companyId;
    private InvestmentRound investmentRound;
    private String phoneNumber;
    private String email;
    private String memo;
    private String customAttribute;
}
