package com.capstone.crmproject.repository;

import com.capstone.crmproject.entity.DealAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DealAttributeRepository extends JpaRepository<DealAttribute, UUID> {
    DealAttribute findByAttributeId(UUID attributeId);
    List<DealAttribute> findByWorkspaceId(UUID workspaceId);
}
