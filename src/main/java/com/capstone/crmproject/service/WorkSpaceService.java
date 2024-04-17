package com.capstone.crmproject.service;

import com.capstone.crmproject.model.WorkSpace;
import com.capstone.crmproject.request.AddMemberRequest;
import com.capstone.crmproject.repository.WorkSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WorkSpaceService {
    private final WorkSpaceRepository workSpaceRepository;
    @Autowired
    public WorkSpaceService(WorkSpaceRepository workSpaceRepository) {
        this.workSpaceRepository = workSpaceRepository;
    }
    public ResponseEntity<String> addMember(AddMemberRequest member){
        WorkSpace workSpace = workSpaceRepository.findByNameAndOwner(member.getWorkSpaceName(),member.getOwner());
        if (workSpace==null) return ResponseEntity.badRequest().body("{\"message\": \"can't find workspace\"}");
        workSpace.getMemberIds().add(member.getMemberName());
        workSpaceRepository.save(workSpace);
        return ResponseEntity.ok().body("{\"message\": \"member added successfully\"}");
    }
}
