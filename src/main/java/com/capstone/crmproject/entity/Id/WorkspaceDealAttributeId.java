package com.capstone.crmproject.entity.Id;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class WorkspaceDealAttributeId implements Serializable {
    private UUID workspaceId;
    private UUID attributeId;

    public WorkspaceDealAttributeId() {
    }

    public WorkspaceDealAttributeId(UUID workspaceId, UUID attributeId) {
        this.workspaceId = workspaceId;
        this.attributeId = attributeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkspaceDealAttributeId that = (WorkspaceDealAttributeId) o;
        return Objects.equals(workspaceId, that.workspaceId) &&
                Objects.equals(attributeId, that.attributeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workspaceId, attributeId);
    }
}
