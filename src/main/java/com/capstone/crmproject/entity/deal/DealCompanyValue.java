package com.capstone.crmproject.entity.deal;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
public class DealCompanyValue extends DealValue {
    private UUID value;
}
