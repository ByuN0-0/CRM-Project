package com.capstone.crmproject.entity.Id;

import com.capstone.crmproject.entity.DealAttributeEntity;
import com.capstone.crmproject.entity.DealEntity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DealValueId implements Serializable {
    private UUID deal;
    private UUID attribute;

    public DealValueId(UUID deal, UUID attribute) {
        this.deal = deal;
        this.attribute = attribute;
    }
    public DealValueId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DealValueId that = (DealValueId) o;
        return Objects.equals(deal, that.deal) &&
                Objects.equals(attribute, that.attribute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deal, attribute);
    }
}
