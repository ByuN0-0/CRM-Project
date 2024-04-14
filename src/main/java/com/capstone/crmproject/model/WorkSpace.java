package com.capstone.crmproject.model;

import com.capstone.crmproject.repository.WorkSpaceRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "workspaces")
@Getter
@Setter
public class WorkSpace {
    String owner;
    String name;
    private List<String> memberIds;
    public WorkSpace(){
        this.memberIds = new ArrayList<>();
    }
}
