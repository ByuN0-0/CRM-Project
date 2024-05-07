package com.capstone.crmproject.repository;

import com.capstone.crmproject.entity.WorkspaceMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMemberEntity, UUID> {
    WorkspaceMemberEntity findByWorkspaceIdAndMemberId(UUID workspaceId, String userId);
    List<WorkspaceMemberEntity> findByWorkspaceId(UUID workspaceId);
    List<WorkspaceMemberEntity> findByMemberId(String userId);
}
