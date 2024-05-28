package com.capstone.crmproject.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class DealDTO {
    private String value;
    private String sortProperty;
    private String sortDirection;
    private LocalDateTime createdAfter;
    private LocalDateTime createdBefore;
    private List<String> filterProperty;
}
