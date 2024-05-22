package com.capstone.crmproject.repository.deal;

import com.capstone.crmproject.entity.deal.DealCompanyValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DealCompanyValueRepository extends JpaRepository<DealCompanyValue, UUID> {
    boolean existsByDealIdAndAttributeId(UUID dealId, UUID attributeId);
    DealCompanyValue findByDealIdAndAttributeId(UUID dealId, UUID attributeId);
}
