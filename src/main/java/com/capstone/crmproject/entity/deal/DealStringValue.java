package com.capstone.crmproject.entity.deal;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class DealStringValue extends DealValue {
    private String value;
}
