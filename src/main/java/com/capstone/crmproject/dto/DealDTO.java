package com.capstone.crmproject.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

import java.sql.Date;
import java.util.UUID;

@Getter
public class DealDTO {
    private UUID id;
    private UUID workspaceId;
    private int companyId;
    private InvestmentRound investmentRound;
    private String phoneNumber;
    private String email;
    private String memo;
    private Date createDate;
    private Date updateDate;
    private String customAttribute;
}
