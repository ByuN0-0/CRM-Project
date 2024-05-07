package com.capstone.crmproject.entity;

import com.capstone.crmproject.dto.InvestmentRound;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;


@Getter
@Setter
@Entity
public class DealEntity {
    @Id
    private UUID workspaceId;
    private int companyId;
    @Enumerated(EnumType.STRING)
    private InvestmentRound investmentRound;
    private String phoneNumber;
    private String email;
    private String memo;
    private Date createDate;
    private Date updateDate;
    private String customAttribute;
}
