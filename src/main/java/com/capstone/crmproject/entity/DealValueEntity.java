package com.capstone.crmproject.entity;

import com.capstone.crmproject.entity.Id.DealValueId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@IdClass(DealValueId.class)
@Entity
public class DealValueEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "deal_id")
    private DealEntity deal;

    @Id
    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private DealAttributeEntity attribute;

    private String value;

    public DealValueEntity(DealEntity deal, DealAttributeEntity attribute, String value) {
        this.deal = deal;
        this.attribute = attribute;
        this.value = value;
    }

    public DealValueEntity() {

    }
}
