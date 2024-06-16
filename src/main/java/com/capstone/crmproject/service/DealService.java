package com.capstone.crmproject.service;

import com.capstone.crmproject.entity.DealAttributeEntity;
import com.capstone.crmproject.entity.DealEntity;
import com.capstone.crmproject.entity.DealValueEntity;
import com.capstone.crmproject.entity.Id.DealValueId;
import com.capstone.crmproject.entity.WorkspaceEntity;
import com.capstone.crmproject.repository.DealAttributeRepository;
import com.capstone.crmproject.repository.DealRepository;
import com.capstone.crmproject.repository.DealValueRepository;
import com.capstone.crmproject.repository.DealWorkspaceRepository;
import jakarta.persistence.EntityNotFoundException;

import jakarta.persistence.criteria.Predicate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class DealService {
    private final DealWorkspaceRepository dealWorkspaceRepository;
    private final DealAttributeRepository dealAttributeRepository;
    private final DealRepository dealRepository;
    private final DealValueRepository dealValueRepository;
    private final WorkspaceService workspaceService;

    public DealService(DealRepository dealRepository,
                       DealWorkspaceRepository dealWorkspaceRepository,
                       DealAttributeRepository dealAttributeRepository,
                       DealValueRepository dealValueRepository,
                       WorkspaceService workspaceService
    ) {
        this.dealRepository = dealRepository;
        this.dealWorkspaceRepository = dealWorkspaceRepository;
        this.dealAttributeRepository = dealAttributeRepository;
        this.dealValueRepository = dealValueRepository;
        this.workspaceService = workspaceService;
    }

    public DealEntity addDealEntity(UUID workspaceId) {
        LocalDateTime now = LocalDateTime.now();
        DealEntity newDeal = new DealEntity(
                workspaceService.getWorkspace(workspaceId),
                now,
                now
        );

        return dealWorkspaceRepository.save(newDeal);
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
            System.out.print("dealValue is null");
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
        deal.setUpdatedDate(LocalDateTime.now());
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
                "Company",
                "Company"
        );
        DealAttributeEntity attribute2 = new DealAttributeEntity(
                workspace,
                "Investment Round",
                "Round"
        );
        DealAttributeEntity attribute3 = new DealAttributeEntity(
                workspace,
                "전화 번호",
                "String"
        );
        DealAttributeEntity attribute4 = new DealAttributeEntity(
                workspace,
                "이메일",
                "String"
        );
        DealAttributeEntity attribute5 = new DealAttributeEntity(
                workspace,
                "메모",
                "String"
        );
        dealAttributeRepository.save(attribute1);
        dealAttributeRepository.save(attribute2);
        dealAttributeRepository.save(attribute3);
        dealAttributeRepository.save(attribute4);
        dealAttributeRepository.save(attribute5);
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
}
