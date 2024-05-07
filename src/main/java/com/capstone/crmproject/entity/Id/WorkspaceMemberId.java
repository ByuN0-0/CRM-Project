package com.capstone.crmproject.entity.Id;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class WorkspaceMemberId implements Serializable {
    private UUID workspaceId;
    private String memberId;

    public WorkspaceMemberId() {}

    public WorkspaceMemberId(UUID workspaceId, String memberId) {
        this.workspaceId = workspaceId;
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkspaceMemberId that = (WorkspaceMemberId) o;
        return Objects.equals(workspaceId, that.workspaceId) &&
                Objects.equals(memberId, that.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workspaceId, memberId);
    }

}
