package com.capstone.crmproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DealValueRepository extends JpaRepository<DealValueRepository, UUID> {
}
