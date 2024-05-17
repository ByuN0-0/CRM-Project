package com.capstone.crmproject.entity;

import com.capstone.crmproject.dto.Round;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Entity
public class DealEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID workspaceId;
    private UUID companyId;
    @Enumerated(EnumType.STRING)
    private Round round;
    private String phoneNumber;
    private String email;
    private String memo;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String customAttribute;
}
