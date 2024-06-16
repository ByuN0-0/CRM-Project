package com.capstone.crmproject.service;

import com.capstone.crmproject.dto.DealAttributeDTO;
import com.capstone.crmproject.entity.DealAttributeEntity;
import com.capstone.crmproject.entity.DealEntity;
import com.capstone.crmproject.entity.DealValueEntity;
import com.capstone.crmproject.entity.Id.DealValueId;
import com.capstone.crmproject.entity.WorkspaceEntity;
import com.capstone.crmproject.repository.*;
import jakarta.persistence.EntityNotFoundException;

import jakarta.persistence.criteria.Predicate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class DealService {
    private final DealWorkspaceRepository dealWorkspaceRepository;
    private final DealAttributeRepository dealAttributeRepository;
    private final DealRepository dealRepository;
    private final DealValueRepository dealValueRepository;
    private final WorkspaceService workspaceService;
    private final WorkspaceRepository workspaceRepository;

    public DealService(DealRepository dealRepository,
                       DealWorkspaceRepository dealWorkspaceRepository,
                       DealAttributeRepository dealAttributeRepository,
                       DealValueRepository dealValueRepository,
                       WorkspaceService workspaceService,
                       WorkspaceRepository workspaceRepository
    ) {
        this.dealRepository = dealRepository;
        this.dealWorkspaceRepository = dealWorkspaceRepository;
        this.dealAttributeRepository = dealAttributeRepository;
        this.dealValueRepository = dealValueRepository;
        this.workspaceService = workspaceService;
        this.workspaceRepository = workspaceRepository;
    }

    @Transactional
    public DealEntity addDealEntity(UUID workspaceId) {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        String dateTimeString = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        DealEntity newDeal = new DealEntity(workspaceService.getWorkspace(workspaceId));
        DealEntity realDeal = dealWorkspaceRepository.save(newDeal);
        getDealAttributeList(workspaceId).forEach(dealAttribute -> {
            if (Objects.equals(dealAttribute.getAttributeName(), "생성 날짜")) {
                DealValueEntity dealValue = new DealValueEntity(realDeal, dealAttribute, dateTimeString);
                dealValueRepository.save(dealValue);
            } else if (Objects.equals(dealAttribute.getAttributeName(), "수정 날짜")) {
                DealValueEntity dealValue = new DealValueEntity(realDeal, dealAttribute, dateTimeString);
                dealValueRepository.save(dealValue);
            }
        });
        return realDeal;
    }

    public List<DealAttributeEntity> getDealAttributeList(UUID workspaceId) {
        WorkspaceEntity workspace = workspaceService.getWorkspace(workspaceId);
        return workspace.getDealAttributes();
    }


    @Transactional
    public DealValueEntity updateDealValue(UUID dealId, UUID attributeId, String value) {
        DealEntity deal = updateDealDate(dealId);
        DealAttributeEntity attribute = dealAttributeRepository.findById(attributeId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find attribute"));

        DealValueId id = new DealValueId(dealId, attributeId);
        DealValueEntity dealValue = dealValueRepository.findById(id).orElse(null);


        if (dealValue == null) {
            dealValue = new DealValueEntity(deal, attribute, value);
        } else {
            dealValue.setValue(value);
        }
        try {
            return dealValueRepository.save(dealValue);
        } catch (DataIntegrityViolationException e) {
            // 중복 키 예외 처리
            // 예외 처리 로직 추가
            System.err.println("Duplicate key error: " + e.getMessage());
            throw new IllegalStateException("Failed to save deal value due to duplicate key");
        }
    }

    public List<DealEntity> getDealList(UUID workspaceId) {
        List<DealEntity> dealEntityList = workspaceService.getWorkspace(workspaceId).getDeals();
        if (dealEntityList == null) {
            throw new IllegalArgumentException("Can't find deal");
        }
        return dealEntityList;
    }

    public DealEntity updateDealDate(UUID dealId) {
        // DealEntity 조회 및 갱신
        DealEntity deal = dealRepository.findById(dealId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find deal"));
        LocalDateTime now = LocalDateTime.now().withNano(0);
        String dateTimeString = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        deal.getDealValues().forEach(dealValue -> {
            if (Objects.equals(dealValue.getAttribute().getAttributeName(), "수정 날짜")) {
                dealValue.setValue(dateTimeString);
                dealValueRepository.save(dealValue);
            }
        });
        return dealRepository.save(deal);
    }

    public void deleteDealEntity(UUID dealId) {
        Optional<DealEntity> dealEntity = dealRepository.findById(dealId);

        if (dealEntity.isEmpty()) {
            throw new EntityNotFoundException("Can't find deal");
        }
        dealRepository.delete(dealEntity.get());
    }

    public void initValue(UUID workspaceId) {
        WorkspaceEntity workspace = workspaceService.getWorkspace(workspaceId);
        DealAttributeEntity attribute1 = new DealAttributeEntity(
                workspace,
                1,
                "Company",
                "Company"
        );
        DealAttributeEntity attribute2 = new DealAttributeEntity(
                workspace,
                2,
                "Investment Round",
                "Round"
        );
        DealAttributeEntity attribute3 = new DealAttributeEntity(
                workspace,
                3,
                "전화 번호",
                "String"
        );
        DealAttributeEntity attribute4 = new DealAttributeEntity(
                workspace,
                4,
                "이메일",
                "String"
        );
        DealAttributeEntity attribute5 = new DealAttributeEntity(
                workspace,
                5,
                "메모",
                "String"
        );
        DealAttributeEntity attribute6 = new DealAttributeEntity(
                workspace,
                6,
                "생성 날짜",
                "Date"
        );
        DealAttributeEntity attribute7 = new DealAttributeEntity(
                workspace,
                7,
                "수정 날짜",
                "Date"
        );
        dealAttributeRepository.save(attribute1);
        dealAttributeRepository.save(attribute2);
        dealAttributeRepository.save(attribute3);
        dealAttributeRepository.save(attribute4);
        dealAttributeRepository.save(attribute5);
        dealAttributeRepository.save(attribute6);
        dealAttributeRepository.save(attribute7);
    }

    public List<DealEntity> getFilteredAndSortedDealList(UUID workspaceId, LocalDateTime createdAfter, LocalDateTime createdBefore, Sort sort) {
        return dealRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("workspace").get("id"), workspaceId));
            if (createdAfter != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdDate"), createdAfter));
            }
            if (createdBefore != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("createdDate"), createdBefore));
            }
            Predicate filterPredicate = cb.and(predicates.toArray(new Predicate[0]));
            query.where(filterPredicate); // 필터링 조건 설정
            return query.getRestriction();
        }, sort);
    }

    public void deleteDealAttribute(UUID attributeId) {
        dealAttributeRepository.deleteById(attributeId);
    }

    public DealAttributeEntity addDealAttribute(UUID workspaceId, DealAttributeDTO dealAttributeDTO) {
        WorkspaceEntity workspace = workspaceService.getWorkspace(workspaceId);

        DealAttributeEntity dealAttribute = new DealAttributeEntity(
                workspace,
                workspace.getDealAttributes().size()+1,
                dealAttributeDTO.getAttributeName(),
                dealAttributeDTO.getAttributeType()
        );
        return dealAttributeRepository.save(dealAttribute);
    }

    public DealAttributeEntity updateDealAttribute(UUID workspaceId, UUID attributeId, DealAttributeDTO dealAttributeDTO) {
        DealAttributeEntity dealAttribute = dealAttributeRepository.findById(attributeId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find attribute"));

        List<DealAttributeEntity> updateAttributes = new ArrayList<>();

        if (dealAttributeDTO.getEndIndex() != dealAttribute.getAttributeOrder() && dealAttributeDTO.getEndIndex() > 0) {
            List<DealAttributeEntity> dealAttributes = getDealAttributeList(workspaceId);
            dealAttribute.setAttributeOrder(dealAttributeDTO.getEndIndex());
            int start = dealAttribute.getAttributeOrder();
            int end = dealAttributeDTO.getEndIndex();
            if (dealAttributeDTO.getEndIndex() > dealAttribute.getAttributeOrder()) {
                for (DealAttributeEntity attribute : dealAttributes) {
                    if (attribute.getAttributeOrder() > start && attribute.getAttributeOrder() <= end) {
                        attribute.setAttributeOrder(attribute.getAttributeOrder() - 1);
                        updateAttributes.add(attribute);
                    }
                }
            } else {
                for (DealAttributeEntity attribute : dealAttributes) {
                    if (attribute.getAttributeOrder() >= end && attribute.getAttributeOrder() < start) {
                        attribute.setAttributeOrder(attribute.getAttributeOrder() + 1);
                        updateAttributes.add(attribute);
                    }
                }
            }
        }

        System.out.println(dealAttributeDTO.getAttributeName() + " " + dealAttributeDTO.getEndIndex());
        dealAttribute.setAttributeName(dealAttributeDTO.getAttributeName());
        dealAttribute.setAttributeType(dealAttributeDTO.getAttributeType());

        updateAttributes.add(dealAttribute);

        // Save all updated attributes in a single transaction
        List<DealAttributeEntity> savedEntities = dealAttributeRepository.saveAll(updateAttributes);

        // Return the updated entity (assuming the first one is dealAttribute itself)
        return savedEntities.get(0);

    }
}
