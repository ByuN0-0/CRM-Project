package com.capstone.crmproject.repository;

import com.capstone.crmproject.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
}
