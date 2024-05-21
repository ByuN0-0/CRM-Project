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

    @Autowired
    public WorkspaceService(WorkspaceRepository workspaceRepository) {
        this.workSpaceRepository = workspaceRepository;
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
    public WorkspaceEntity createWorkspace(String workspaceName, UserEntity userEntity) {
        if (workspaceName == null || workspaceName.isEmpty()) {
            throw new IllegalArgumentException("Workspace name is empty");
        }
        if (userEntity == null) {
            throw new IllegalArgumentException("User is null");
        }
        WorkspaceEntity workSpace = new WorkspaceEntity();
        workSpace.setName(workspaceName);
        workSpace.setOwnerId(userEntity.getUsername());
        return workSpaceRepository.save(workSpace);
    }
}
