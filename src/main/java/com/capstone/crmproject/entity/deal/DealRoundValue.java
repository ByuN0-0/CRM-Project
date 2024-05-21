package com.capstone.crmproject.entity.deal;

import com.capstone.crmproject.dto.Round;
import jakarta.persistence.Entity;

@Entity
public class DealRoundValue extends DealEntity {
    private Round value;
}
