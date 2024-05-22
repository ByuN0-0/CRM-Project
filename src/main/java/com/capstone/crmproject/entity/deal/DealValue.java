package com.capstone.crmproject.entity.deal;

import com.capstone.crmproject.entity.Id.DealValueId;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
@IdClass(DealValueId.class)
public abstract class DealValue {
    @Id
    private UUID dealId;
    @Id
    private UUID attributeId;
}
