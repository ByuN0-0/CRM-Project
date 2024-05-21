package com.capstone.crmproject.repository;

import com.capstone.crmproject.entity.DealAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DealAttributeRepository extends JpaRepository<DealAttribute, UUID> {
}
