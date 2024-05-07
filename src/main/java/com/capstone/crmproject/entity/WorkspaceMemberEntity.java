package com.capstone.crmproject.entity;

import com.capstone.crmproject.entity.Id.WorkspaceMemberId;
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
@IdClass(WorkspaceMemberId.class)
public class WorkspaceMemberEntity {

    @Id
    @Column(name = "workspace_id")
    private UUID workspaceId;

    @Id
    @Column(name = "member_id")
    private String memberId;
}
