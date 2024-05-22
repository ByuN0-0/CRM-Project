package com.capstone.crmproject.repository.deal;

import com.capstone.crmproject.entity.deal.DealCompanyValue;
import com.capstone.crmproject.entity.deal.DealRoundValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DealRoundValueRepository extends JpaRepository<DealRoundValue, UUID> {
    boolean existsByDealIdAndAttributeId(UUID dealId, UUID attributeId);
    DealRoundValue findByDealIdAndAttributeId(UUID dealId, UUID attributeId);
}
