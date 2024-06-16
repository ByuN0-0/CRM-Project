package com.capstone.crmproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;


@Setter
@Getter
@Entity
public class DealAttributeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID attributeId;
    @ManyToOne
    @JoinColumn(name = "workspace_id")
    private WorkspaceEntity workspace;
    private int attributeOrder;
    private String attributeName;
    private String attributeType;
    @OneToMany(mappedBy = "attribute", cascade = CascadeType.REMOVE)
    private List<DealValueEntity> dealValues;

    public DealAttributeEntity(WorkspaceEntity workspace, int attributeOrder, String attributeName, String attributeType) {
        this.workspace = workspace;
        this.attributeOrder = attributeOrder;
        this.attributeName = attributeName;
        this.attributeType = attributeType;
    }

    public DealAttributeEntity() {

    }
}
