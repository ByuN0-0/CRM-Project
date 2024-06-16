package com.capstone.crmproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Getter
@Entity
public class DealEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID DealId;

    @ManyToOne()
    @JoinColumn(name = "workspace_id")
    private WorkspaceEntity workspace;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    @OneToMany(mappedBy = "deal", cascade = CascadeType.REMOVE)
    private List<DealValueEntity> dealValues;

    public DealEntity() {
    }

    public DealEntity(WorkspaceEntity workspace, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.workspace = workspace;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}
