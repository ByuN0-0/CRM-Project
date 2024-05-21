package com.capstone.crmproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class DealAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID attributeId;
    private String attribute;
}
