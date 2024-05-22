package com.capstone.crmproject.entity.deal;

import com.capstone.crmproject.dto.Round;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class DealRoundValue extends DealValue {
    private Round value;
}
