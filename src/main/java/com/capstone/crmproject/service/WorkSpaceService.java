package com.capstone.crmproject.service;

import com.capstone.crmproject.model.WorkSpace;
import com.capstone.crmproject.request.AddMemberRequest;
import com.capstone.crmproject.repository.WorkSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkSpaceService {
    private final WorkSpaceRepository workSpaceRepository;
    @Autowired
    public WorkSpaceService(WorkSpaceRepository workSpaceRepository) {
        this.workSpaceRepository = workSpaceRepository;
    }
    public boolean addMember(AddMemberRequest member){
        WorkSpace workSpace = workSpaceRepository.findByNameAndOwner(member.getMemberName(),member.getOwner());
        workSpace.getMemberIds().add(member.getMemberName());
        workSpaceRepository.save(workSpace);
        return true;
    }
}
