package com.capstone.crmproject.service;


import com.capstone.crmproject.entity.UserEntity;
import com.capstone.crmproject.entity.WorkspaceEntity;
import com.capstone.crmproject.entity.WorkspaceMemberEntity;
import com.capstone.crmproject.repository.WorkspaceMemberRepository;
import com.capstone.crmproject.repository.WorkspaceRepository;

import com.capstone.crmproject.request.UserRegisterRequest;
import com.capstone.crmproject.request.WorkspaceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
        WorkspaceEntity workSpace = new WorkspaceEntity();
        workSpace.setName(workspaceName);
        workSpace.setOwnerId(userEntity.getUsername());
        return workSpaceRepository.save(workSpace);
    }
}
