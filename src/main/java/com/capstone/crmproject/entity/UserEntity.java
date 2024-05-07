package com.capstone.crmproject.entity;

import com.capstone.crmproject.security.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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
