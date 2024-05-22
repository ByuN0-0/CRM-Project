package com.capstone.crmproject.service;

import com.capstone.crmproject.dto.Round;
import com.capstone.crmproject.entity.DealAttribute;
import com.capstone.crmproject.entity.DealEntity;
import com.capstone.crmproject.entity.deal.DealCompanyValue;
import com.capstone.crmproject.entity.deal.DealRoundValue;
import com.capstone.crmproject.entity.deal.DealStringValue;
import com.capstone.crmproject.entity.deal.DealValue;
import com.capstone.crmproject.repository.DealAttributeRepository;
import com.capstone.crmproject.repository.DealRepository;
import com.capstone.crmproject.repository.DealWorkspaceRepository;
import com.capstone.crmproject.repository.deal.DealCompanyValueRepository;
import com.capstone.crmproject.repository.deal.DealRoundValueRepository;
import com.capstone.crmproject.repository.deal.DealStringValueRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DealService {
    private final DealWorkspaceRepository dealWorkspaceRepository;
    private final DealRoundValueRepository dealRoundValueRepository;
    private final DealStringValueRepository dealStringValueRepository;
    private final DealCompanyValueRepository dealCompanyValueRepository;
    private final DealAttributeRepository dealAttributeRepository;
    private final DealRepository dealRepository;

    public DealService(DealRepository dealRepository,
                       DealWorkspaceRepository dealWorkspaceRepository,
                       DealCompanyValueRepository dealCompanyValueRepository,
                       DealStringValueRepository dealStringValueRepository,
                       DealRoundValueRepository dealRoundValueRepository,
                       DealAttributeRepository dealAttributeRepository) {
        this.dealRepository = dealRepository;
        this.dealWorkspaceRepository = dealWorkspaceRepository;
        this.dealCompanyValueRepository = dealCompanyValueRepository;
        this.dealStringValueRepository = dealStringValueRepository;
        this.dealRoundValueRepository = dealRoundValueRepository;
        this.dealAttributeRepository = dealAttributeRepository;
    }

    public DealEntity addDealEntity(UUID workspaceId) {
        LocalDateTime now = LocalDateTime.now();
        DealEntity newDeal = new DealEntity();
        newDeal.setWorkspaceId(workspaceId);
        newDeal.setCreatedDate(now);
        newDeal.setUpdatedDate(now);
        return dealWorkspaceRepository.save(newDeal);
    }

    public List<DealAttribute> getDealAttributeList(UUID workspaceId) {
        return dealAttributeRepository.findByWorkspaceId(workspaceId);
    }

    public DealValue updateDealValue(UUID dealId, UUID attributeId, String value) {
        String attributeType = dealAttributeRepository.findByAttributeId(attributeId).getAttributeType();
        switch (attributeType) {
            case "Company" -> {
                if (dealCompanyValueRepository.existsByDealIdAndAttributeId(dealId, attributeId)) {
                    DealCompanyValue dealCompanyValue = dealCompanyValueRepository.findByDealIdAndAttributeId(dealId, attributeId);
                    dealCompanyValue.setValue(UUID.fromString(value));
                    return dealCompanyValueRepository.save(dealCompanyValue);
                } else {
                    DealCompanyValue newDealValue = new DealCompanyValue();
                    newDealValue.setDealId(dealId);
                    newDealValue.setAttributeId(attributeId);
                    newDealValue.setValue(UUID.fromString(value));
                    return dealCompanyValueRepository.save(newDealValue);
                }
            }
            case "String" -> {
                if (dealStringValueRepository.existsByDealIdAndAttributeId(dealId, attributeId)) {
                    DealStringValue dealStringValue = dealStringValueRepository.findByDealIdAndAttributeId(dealId, attributeId);
                    dealStringValue.setValue(value);
                    return dealStringValueRepository.save(dealStringValue);
                } else {
                    DealStringValue newDealValue = new DealStringValue();
                    newDealValue.setDealId(dealId);
                    newDealValue.setAttributeId(attributeId);
                    newDealValue.setValue(value);
                    return dealStringValueRepository.save(newDealValue);
                }
            }
            case "Round" -> {
                if (dealRoundValueRepository.existsByDealIdAndAttributeId(dealId, attributeId)) {
                    DealRoundValue dealRoundValue = dealRoundValueRepository.findByDealIdAndAttributeId(dealId, attributeId);
                    dealRoundValue.setValue(Round.valueOf(value));
                    return dealRoundValueRepository.save(dealRoundValue);
                } else {
                    DealRoundValue newDealValue = new DealRoundValue();
                    newDealValue.setDealId(dealId);
                    newDealValue.setAttributeId(attributeId);
                    newDealValue.setValue(Round.valueOf(value));
                    return dealRoundValueRepository.save(newDealValue);
                }
            }
        }
        return null;
    }

    public List<DealEntity> getDealList(UUID workspaceId) {
        List<DealEntity> dealEntityList = dealRepository.findByWorkspaceId(workspaceId);
        if (dealEntityList == null) {
            throw new IllegalArgumentException("Can't find deal");
        }
        return dealEntityList;
    }

    public void deleteDealEntity(UUID dealId) {
        Optional<DealEntity> dealEntity = dealRepository.findById(dealId);

        if (dealEntity.isEmpty()) {
            throw new EntityNotFoundException("Can't find deal");
        }
        dealRepository.delete(dealEntity.get());
    }

    public void initValue(UUID workspaceId) {
        DealAttribute attribute1 = new DealAttribute();
        attribute1.setWorkspaceId(workspaceId);
        attribute1.setAttributeName("Company");
        attribute1.setAttributeType("Company");

        DealAttribute attribute2 = new DealAttribute();
        attribute2.setWorkspaceId(workspaceId);
        attribute2.setAttributeName("investment_round");
        attribute2.setAttributeType("Round");

        DealAttribute attribute3 = new DealAttribute();
        attribute3.setWorkspaceId(workspaceId);
        attribute3.setAttributeName("전화번호");
        attribute3.setAttributeType("String");

        DealAttribute attribute4 = new DealAttribute();
        attribute4.setWorkspaceId(workspaceId);
        attribute4.setAttributeName("이메일");
        attribute4.setAttributeType("String");

        DealAttribute attribute5 = new DealAttribute();
        attribute5.setWorkspaceId(workspaceId);
        attribute5.setAttributeName("메모");
        attribute5.setAttributeType("String");

        dealAttributeRepository.save(attribute1);
        dealAttributeRepository.save(attribute2);
        dealAttributeRepository.save(attribute3);
        dealAttributeRepository.save(attribute4);
        dealAttributeRepository.save(attribute5);
    }
}
