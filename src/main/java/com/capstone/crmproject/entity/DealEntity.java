package com.capstone.crmproject.entity;

import com.capstone.crmproject.dto.InvestmentRound;
import com.capstone.crmproject.entity.Id.DealId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Entity
@IdClass(DealId.class)
public class DealEntity {
    @Id
    private UUID workspaceId;
    @Id
    private UUID attributeId;
    private String value;
}
