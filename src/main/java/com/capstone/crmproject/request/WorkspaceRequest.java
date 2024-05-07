package com.capstone.crmproject.request;

import lombok.Getter;

import java.util.UUID;

@Getter
public class WorkspaceRequest {
    String username;
    UUID workspaceId;

}
