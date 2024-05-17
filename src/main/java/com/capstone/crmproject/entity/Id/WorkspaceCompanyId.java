package com.capstone.crmproject.entity.Id;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class WorkspaceCompanyId implements Serializable {
    private UUID workspaceId;
    private UUID companyId;

    public WorkspaceCompanyId() {
    }

    public WorkspaceCompanyId(UUID workspaceId, UUID companyId) {
        this.workspaceId = workspaceId;
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkspaceCompanyId that = (WorkspaceCompanyId) o;
        return Objects.equals(workspaceId, that.workspaceId) &&
                Objects.equals(companyId, that.companyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workspaceId, companyId);
    }
}
