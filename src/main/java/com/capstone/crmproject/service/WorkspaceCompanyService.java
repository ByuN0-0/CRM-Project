package com.capstone.crmproject.service;

import com.capstone.crmproject.entity.WorkspaceCompanyEntity;
import com.capstone.crmproject.repository.WorkspaceCompanyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class WorkspaceCompanyService {
    private final WorkspaceCompanyRepository workspaceCompanyRepository;

    public WorkspaceCompanyService(WorkspaceCompanyRepository workspaceCompanyRepository) {
        this.workspaceCompanyRepository = workspaceCompanyRepository;
    }

    @Transactional
    public WorkspaceCompanyEntity addCompany(UUID workspaceId, UUID companyId) {
        WorkspaceCompanyEntity workspaceCompanyEntity = new WorkspaceCompanyEntity();
        workspaceCompanyEntity.setWorkspaceId(workspaceId);
        workspaceCompanyEntity.setCompanyId(companyId);

        if (workspaceCompanyRepository.findByWorkspaceIdAndCompanyId(workspaceId, companyId) != null) {
            throw new IllegalArgumentException("Company already exists");
        }
        return workspaceCompanyRepository.save(workspaceCompanyEntity);
    }

    @Transactional
    public List<WorkspaceCompanyEntity> getCompanyList(UUID workspaceId) {
        List<WorkspaceCompanyEntity> companyEntityList = workspaceCompanyRepository.findByWorkspaceId(workspaceId);
        if (companyEntityList == null) {
            throw new IllegalArgumentException("Can't find workspace");
        }
        return companyEntityList;
    }
}
