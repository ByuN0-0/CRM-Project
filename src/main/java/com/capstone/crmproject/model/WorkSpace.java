package com.capstone.crmproject.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "workspaces")
@Builder
@Getter
@Setter
public class WorkSpace {
    private String owner;
    private String name;
    @Singular
    private List<String> memberIds;
    public WorkSpace(){}
}
