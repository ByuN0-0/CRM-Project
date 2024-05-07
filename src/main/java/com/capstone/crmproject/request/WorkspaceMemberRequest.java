package com.capstone.crmproject.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class WorkspaceMemberRequest {
    UUID workspaceId;
    UUID memberId;
}
