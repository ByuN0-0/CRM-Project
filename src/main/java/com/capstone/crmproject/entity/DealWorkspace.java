package com.capstone.crmproject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@Entity
public class DealWorkspace {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID DealId;
    private UUID workspaceId;
}
