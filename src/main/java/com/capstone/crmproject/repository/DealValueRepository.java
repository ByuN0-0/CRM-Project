package com.capstone.crmproject.repository;

import com.capstone.crmproject.entity.DealAttributeEntity;
import com.capstone.crmproject.entity.DealEntity;
import com.capstone.crmproject.entity.DealValueEntity;
import com.capstone.crmproject.entity.Id.DealValueId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DealValueRepository extends JpaRepository<DealValueEntity, DealValueId> {
}
