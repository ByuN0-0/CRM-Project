package com.capstone.crmproject.repository;

import com.capstone.crmproject.entity.WorkspaceCompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkspaceCompanyRepository extends JpaRepository<WorkspaceCompanyEntity, UUID> {
    WorkspaceCompanyEntity findByWorkspaceIdAndCompanyId(UUID workspaceId, UUID companyId);
    List<WorkspaceCompanyEntity> findByWorkspaceId(UUID workspaceId);
}
