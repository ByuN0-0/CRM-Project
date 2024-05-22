package com.capstone.crmproject.repository.deal;

import com.capstone.crmproject.entity.deal.DealStringValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DealStringValueRepository extends JpaRepository<DealStringValue, UUID> {
    boolean existsByDealIdAndAttributeId(UUID dealId, UUID attributeId);
    DealStringValue findByDealIdAndAttributeId(UUID dealId, UUID attributeId);
}
