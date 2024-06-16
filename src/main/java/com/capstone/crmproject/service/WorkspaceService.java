package com.capstone.crmproject.service;


import com.capstone.crmproject.entity.UserEntity;
import com.capstone.crmproject.entity.WorkspaceEntity;
import com.capstone.crmproject.repository.WorkspaceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class WorkspaceService {
    private final WorkspaceRepository workSpaceRepository;
    private final UserService userService;

    @Autowired
    public WorkspaceService(WorkspaceRepository workspaceRepository, UserService userService) {
        this.workSpaceRepository = workspaceRepository;
        this.userService = userService;
    }

    @Transactional
    public WorkspaceEntity getWorkspace(UUID workspaceId) {
        WorkspaceEntity find = workSpaceRepository.findByWorkspaceId(workspaceId);
        if (find == null) {
            throw new IllegalArgumentException("Workspace not found");
        }
        return find;
    }

    @Transactional
    public WorkspaceEntity createWorkspace(String workspaceName, String ownerId) {
        if (workspaceName == null || workspaceName.isEmpty()) {
            throw new IllegalArgumentException("Workspace name is empty");
        }
        if (ownerId == null) {
            throw new IllegalArgumentException("User is null");
        }
        WorkspaceEntity workSpace = new WorkspaceEntity(
                workspaceName,
                ownerId
        );

        return workSpaceRepository.save(workSpace);
    }

    public boolean isMember(UUID workspaceId, String username) {
        WorkspaceEntity workspace = getWorkspace(workspaceId);
        UserEntity user = userService.getUser(username);
        return workspace.getUsers().contains(user);
    }

    @Transactional
    public void addMember(UUID workspaceId, String memberId) {
        WorkspaceEntity workspace = getWorkspace(workspaceId);
        UserEntity user = userService.getUser(memberId);
        workspace.getUsers().add(user);
    }
}
