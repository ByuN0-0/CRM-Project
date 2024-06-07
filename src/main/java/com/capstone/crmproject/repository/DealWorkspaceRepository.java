package com.capstone.crmproject.repository;

import com.capstone.crmproject.entity.DealEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DealWorkspaceRepository extends JpaRepository<DealEntity, UUID> {
}
