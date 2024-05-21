package com.capstone.crmproject.entity;

import com.capstone.crmproject.entity.Id.DealValueId;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Entity
public class DealEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID DealId;
    private UUID workspaceId;
}
