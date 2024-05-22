package com.capstone.crmproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Entity
public class DealEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID DealId;
    private UUID workspaceId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
