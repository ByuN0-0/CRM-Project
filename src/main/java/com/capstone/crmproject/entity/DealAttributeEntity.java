package com.capstone.crmproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;



@Getter
@Entity
public class DealAttributeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID attributeId;
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private WorkspaceEntity workspace;
    private String attributeName;
    private String attributeType;

    public DealAttributeEntity(WorkspaceEntity workspace, String attributeName, String attributeType) {
        this.workspace = workspace;
        this.attributeName = attributeName;
        this.attributeType = attributeType;
    }

    public DealAttributeEntity() {

    }
}
