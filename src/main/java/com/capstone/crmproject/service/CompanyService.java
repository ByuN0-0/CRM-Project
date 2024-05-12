package com.capstone.crmproject.service;

import com.capstone.crmproject.dto.CompanyDTO;
import com.capstone.crmproject.entity.CompanyEntity;
import com.capstone.crmproject.repository.CompanyRepository;
import com.capstone.crmproject.request.CompanyRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Transactional
    public CompanyEntity getCompany(UUID companyId) {
        CompanyEntity companyEntity = companyRepository.findByCompanyId(companyId);
        if (companyEntity == null) {
            throw new IllegalArgumentException("Can't find company");
        }
        return companyEntity;
    }
    @Transactional
    public CompanyEntity insertCompany(CompanyDTO companyDTO) {
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setCompanyName(companyDTO.getCompanyName());
        return companyRepository.save(companyEntity);
    }

    @Transactional
    public CompanyEntity updateCompany(UUID companyId, CompanyDTO companyDTO) {
        CompanyEntity companyEntity = companyRepository.findByCompanyId(companyId);
        if (companyEntity == null) {
            throw new IllegalArgumentException("Can't find workspace");
        }
        companyEntity.setCompanyName(companyDTO.getCompanyName());
        return companyRepository.save(companyEntity);
    }
}
