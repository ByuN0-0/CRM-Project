package com.capstone.crmproject.entity.deal;

import jakarta.persistence.Entity;

import java.util.UUID;

@Entity
public class DealCompanyValue extends DealEntity{
    private UUID value;
}
