package com.capstone.crmproject.service;

import com.capstone.crmproject.dto.DealDTO;
import com.capstone.crmproject.entity.DealWorkspace;
import com.capstone.crmproject.repository.DealAttributeRepository;
import com.capstone.crmproject.repository.DealRepository;
import com.capstone.crmproject.repository.DealValueRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DealService {
    private final DealRepository dealRepository;
    private final DealValueRepository dealValueRepository;
    private final DealAttributeRepository dealAttributeRepository;
    public DealService(DealRepository dealRepository, DealValueRepository dealValueRepository, DealAttributeRepository dealAttributeRepository){
        this.dealRepository = dealRepository;
        this.dealValueRepository = dealValueRepository;
        this.dealAttributeRepository = dealAttributeRepository;
    }
    public DealWorkspace addDealEntity(UUID workspaceId){
        DealWorkspace newDeal = new DealWorkspace();
        newDeal.setWorkspaceId(workspaceId);
        return dealRepository.save(newDeal);
    }

    public DealWorkspace updateDealEntity(UUID dealId, DealDTO dealDTO) {
        Optional<DealWorkspace> optionalDeal= dealRepository.findById(dealId);
        if (optionalDeal.isEmpty()) throw new EntityNotFoundException("Deal not found");

    }

    public List<DealWorkspace> getDealList(UUID workspaceId) {
        List<DealWorkspace> dealEntityList = dealRepository.findByWorkspaceId(workspaceId);
        if (dealEntityList == null) {
            throw new IllegalArgumentException("Can't find deal");
        }
        return dealEntityList;
    }

    public void deleteDealEntity(UUID dealId) {
        Optional<DealWorkspace> optionalDeal = dealRepository.findById(dealId);
        if (optionalDeal.isEmpty()) throw new EntityNotFoundException("Deal not found");
        dealRepository.delete(optionalDeal.get());
    }

    public void initValue(UUID dealId){
        deal
    }
}
