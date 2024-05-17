package com.capstone.crmproject.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
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

}
