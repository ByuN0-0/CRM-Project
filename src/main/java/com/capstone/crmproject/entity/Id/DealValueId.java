package com.capstone.crmproject.entity.Id;

import com.capstone.crmproject.entity.DealAttributeEntity;
import com.capstone.crmproject.entity.DealEntity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DealValueId implements Serializable {
    private DealEntity deal;
    private DealAttributeEntity attribute;

    public DealValueId() {
    }

    public DealValueId(DealEntity deal, DealAttributeEntity attribute) {
        this.deal = deal;
        this.attribute = attribute;
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
