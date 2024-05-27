package com.capstone.crmproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Entity
public class WorkspaceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "workspace_id")
    private UUID workspaceId;

    @Column(name = "workspace_name")
    private String name;

    @Column(name = "owner_id")
    private String ownerId;

    @ManyToMany(mappedBy = "workspaces")
    private List<UserEntity> users;

    @OneToMany(mappedBy = "workspace")
    private List<DealEntity> deals;

    @OneToMany(mappedBy = "workspace")
    private List<DealAttributeEntity> dealAttributes;


    public WorkspaceEntity() {
    }

    public WorkspaceEntity(String name, String ownerId) {
        this.name = name;
        this.ownerId = ownerId;
    }
}
