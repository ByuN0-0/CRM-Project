package com.capstone.crmproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Setter
@Getter
@Entity
public class DealAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID attributeId;
    private UUID workspaceId;
    private String attributeName;
    private String attributeType;
}
