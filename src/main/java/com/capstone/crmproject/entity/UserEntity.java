package com.capstone.crmproject.entity;

import com.capstone.crmproject.security.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class UserEntity {
    @Id
    @Column(name = "user_id")
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @ManyToMany
    @JoinTable(
            name = "user_workspace",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "workspace_id")
    )
    private List<WorkspaceEntity> workspaces;

    public UserEntity() {
    }

    public UserEntity(String username, String password, UserRole role, WorkspaceEntity workspace) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.workspaces = new ArrayList<>();
        this.workspaces.add(workspace);
    }
}
