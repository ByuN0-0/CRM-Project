package com.capstone.crmproject.service;

import com.capstone.crmproject.dto.DealDTO;
import com.capstone.crmproject.entity.DealEntity;
import com.capstone.crmproject.repository.DealRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DealService {
    private final DealRepository dealRepository;
    public DealService(DealRepository dealRepository){
        this.dealRepository = dealRepository;
    }
    public DealEntity addDealEntity(UUID workspaceId){
        DealEntity newDeal = new DealEntity();
        newDeal.setWorkspaceId(workspaceId);
        return dealRepository.save(newDeal);
    }

    public DealEntity updateDealEntity(UUID dealId, DealDTO dealDTO) {
        Optional<DealEntity> optionalDeal= dealRepository.findById(dealId);
        if (optionalDeal.isEmpty()) throw new EntityNotFoundException("Deal not found");

    }

    public List<DealEntity> getDealList(UUID workspaceId) {
        List<DealEntity> dealEntityList = dealRepository.findByWorkspaceId(workspaceId);
        if (dealEntityList == null) {
            throw new IllegalArgumentException("Can't find deal");
        }
        return dealEntityList;
    }

    public void deleteDealEntity(UUID dealId) {
        Optional<DealEntity> optionalDeal = dealRepository.findById(dealId);
        if (optionalDeal.isEmpty()) throw new EntityNotFoundException("Deal not found");
        dealRepository.delete(optionalDeal.get());
    }

    public void initValue(DealEntity deal){

    }
}
