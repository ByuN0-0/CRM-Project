package com.capstone.crmproject.entity.Id;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class DealId implements Serializable {
    private UUID attributeId;
    private UUID workspaceId;

    public DealId() {
    }

    public DealId(UUID attributeId, UUID workspaceId) {
        this.attributeId = attributeId;
        this.workspaceId = workspaceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DealId that = (DealId) o;
        return Objects.equals(attributeId, that.attributeId) &&
                Objects.equals(workspaceId, that.workspaceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeId, workspaceId);
    }
}
