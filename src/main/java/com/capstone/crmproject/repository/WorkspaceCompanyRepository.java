package com.capstone.crmproject.repository;

import com.capstone.crmproject.entity.WorkspaceCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkspaceCompanyRepository extends JpaRepository<WorkspaceCompany, UUID> {
    WorkspaceCompany findByWorkspaceIdAndCompanyId(UUID workspaceId, UUID companyId);
    List<WorkspaceCompany> findByWorkspaceId(UUID workspaceId);
}
