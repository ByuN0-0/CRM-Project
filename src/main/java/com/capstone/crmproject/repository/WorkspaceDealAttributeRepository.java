package com.capstone.crmproject.repository;

import com.capstone.crmproject.entity.WorkspaceDealAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WorkspaceDealAttributeRepository extends JpaRepository<WorkspaceDealAttribute, UUID> {
}
