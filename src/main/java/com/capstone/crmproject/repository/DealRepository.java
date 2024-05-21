package com.capstone.crmproject.repository;

import com.capstone.crmproject.entity.DealWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DealRepository extends JpaRepository<DealWorkspace, UUID> {
    List<DealWorkspace> findByWorkspaceId(UUID workspaceId);
    Optional<DealWorkspace> findById(UUID id);
}
