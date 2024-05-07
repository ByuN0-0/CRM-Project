package com.capstone.crmproject.repository;

import com.capstone.crmproject.entity.WorkspaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkspaceRepository extends JpaRepository<WorkspaceEntity, UUID> {
    WorkspaceEntity findByWorkspaceId(UUID id);
    String findWorkspaceNameByWorkspaceId(UUID id);
}
