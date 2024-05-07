package com.capstone.crmproject.entity;

import com.capstone.crmproject.dto.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private String username;
    private String password;
    private UserRole role;
}
