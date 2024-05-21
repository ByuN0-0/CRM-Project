package com.capstone.crmproject.repository;

import com.capstone.crmproject.entity.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, UUID> {
    WorkspaceMember findByWorkspaceIdAndMemberId(UUID workspaceId, String userId);
    List<WorkspaceMember> findByWorkspaceId(UUID workspaceId);
    List<WorkspaceMember> findByMemberId(String userId);

    boolean existsByWorkspaceIdAndMemberId(UUID workspaceId, String username);
}
