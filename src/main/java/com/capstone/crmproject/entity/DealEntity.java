package com.capstone.crmproject.entity;

import com.capstone.crmproject.dto.InvestmentRound;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Entity
public class DealEntity {
    @Id
    private UUID id;
    private UUID workspaceId;
    private UUID companyId;
    @Enumerated(EnumType.STRING)
    private InvestmentRound investmentRound;
    private String phoneNumber;
    private String email;
    private String memo;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String customAttribute;
}
