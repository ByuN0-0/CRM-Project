package com.capstone.crmproject.entity;

import com.capstone.crmproject.entity.Id.DealValueId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.util.UUID;

@Entity
@IdClass(DealValueId.class)
public class DealValueEntity {
    @Id
    private UUID dealId;
    @Id
    private UUID attributeId;
    private String value;

}
