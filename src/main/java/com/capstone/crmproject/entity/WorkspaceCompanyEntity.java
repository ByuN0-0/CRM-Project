package com.capstone.crmproject.entity;

import com.capstone.crmproject.entity.Id.WorkspaceCompanyId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@IdClass(WorkspaceCompanyId.class)
public class WorkspaceCompanyEntity {
    @Id
    @Column(name = "workspace_id")
    private UUID workspaceId;

    @Id
    @Column(name = "company_id")
    private UUID companyId;

}
