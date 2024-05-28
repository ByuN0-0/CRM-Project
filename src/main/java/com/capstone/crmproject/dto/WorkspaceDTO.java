package com.capstone.crmproject.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
public class WorkspaceDTO {
    private UUID workspaceId;
    private String workspaceName;
    private String ownerId;
    private String memberId;
}
