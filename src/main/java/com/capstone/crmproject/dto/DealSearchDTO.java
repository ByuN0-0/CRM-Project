package com.capstone.crmproject.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class DealSearchDTO {
    private String sortProperty;
    private String sortDirection;
    private LocalDateTime createdAfter;
    private LocalDateTime createdBefore;
    private List<String> filterProperty;
}
