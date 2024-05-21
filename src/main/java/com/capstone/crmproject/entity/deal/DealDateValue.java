package com.capstone.crmproject.entity.deal;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class DealDateValue extends DealEntity {
    private LocalDateTime value;
}
