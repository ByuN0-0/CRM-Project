package com.capstone.crmproject.service;

import com.capstone.crmproject.dto.DealDTO;
import com.capstone.crmproject.entity.DealEntity;
import com.capstone.crmproject.repository.DealRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class DealService {
    private final DealRepository dealRepository;
    public DealService(DealRepository dealRepository){
        this.dealRepository = dealRepository;
    }
    public DealEntity addDealEntity(DealDTO dealDTO){
        LocalDateTime date = LocalDateTime.now();
        DealEntity dealEntity = new DealEntity();
        dealEntity.setCompanyId(dealDTO.getCompanyId());
        dealEntity.setMemo(dealDTO.getMemo());
        dealEntity.setCreateDate(date);
        dealEntity.setUpdateDate(date);
        dealEntity.setInvestmentRound(dealDTO.getInvestmentRound());
        dealEntity.setEmail(dealDTO.getEmail());
        dealEntity.setPhoneNumber(dealDTO.getEmail());
        dealEntity.setCustomAttribute(dealDTO.getCustomAttribute());

        return dealRepository.save(dealEntity);
    }

    public DealEntity updateDealEntity(DealDTO dealDTO) {
        Optional<DealEntity> optionalDeal= dealRepository.findById(dealDTO.getId());
        if (optionalDeal.isEmpty()) throw new EntityNotFoundException("Deal not found");
        DealEntity deal = optionalDeal.get();
        deal.setCompanyId(dealDTO.getCompanyId());
        deal.setMemo(dealDTO.getMemo());
        deal.setUpdateDate(LocalDateTime.now());
        deal.setInvestmentRound(dealDTO.getInvestmentRound());
        deal.setEmail(dealDTO.getEmail());
        deal.setPhoneNumber(dealDTO.getEmail());
        deal.setCustomAttribute(dealDTO.getCustomAttribute());
        return dealRepository.save(deal);
    }
}
