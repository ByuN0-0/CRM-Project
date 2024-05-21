package com.capstone.crmproject.entity;

import com.capstone.crmproject.entity.Id.WorkspaceDealAttributeId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.util.UUID;

@Entity
@IdClass(WorkspaceDealAttributeId.class)
public class WorkspaceDealAttribute {
    @Id
    private UUID workspaceId;
    @Id
    private UUID attributeId;
}
