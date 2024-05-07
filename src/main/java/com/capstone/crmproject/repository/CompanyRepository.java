package com.capstone.crmproject.repository;

import com.capstone.crmproject.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    CompanyEntity findByCompanyId(UUID companyId);
}
