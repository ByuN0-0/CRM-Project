package com.capstone.crmproject.entity.deal;

import com.capstone.crmproject.entity.Id.DealValueId;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

@MappedSuperclass
@IdClass(DealValueId.class)
public abstract class DealEntity {
    @Id
    private UUID dealId;
    @Id
    private UUID attributeId;
}
