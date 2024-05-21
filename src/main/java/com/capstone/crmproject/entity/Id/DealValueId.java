package com.capstone.crmproject.entity.Id;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DealValueId implements Serializable {
    private UUID dealId;
    private UUID attributeId;

    public DealValueId() {
    }

    public DealValueId(UUID dealId, UUID attributeId) {
        this.dealId = dealId;
        this.attributeId = attributeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DealValueId that = (DealValueId) o;
        return Objects.equals(dealId, that.dealId) &&
                Objects.equals(attributeId, that.attributeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dealId, attributeId);
    }
}
