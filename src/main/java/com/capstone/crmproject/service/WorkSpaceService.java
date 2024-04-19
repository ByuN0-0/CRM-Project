package com.capstone.crmproject.service;

import com.capstone.crmproject.model.WorkSpace;
import com.capstone.crmproject.request.AddMemberRequest;
import com.capstone.crmproject.repository.WorkSpaceRepository;
import com.capstone.crmproject.request.UserRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkSpaceService {
    private final WorkSpaceRepository workSpaceRepository;

    @Autowired
    public WorkSpaceService(WorkSpaceRepository workSpaceRepository, @Value("${spring.data.mongodb.uri}") String mongoUri) {
        this.workSpaceRepository = workSpaceRepository;
    }

    @Transactional
    public WorkSpace createWorkSpace(UserRegisterRequest userRegisterRequest) {
        WorkSpace workSpace = WorkSpace.builder()
                .name(userRegisterRequest.getWorkSpaceName())
                .owner(userRegisterRequest.getUserName())
                .memberId(userRegisterRequest.getUserName())
                .build();
        return workSpaceRepository.save(workSpace);
    }

    @Transactional
    public WorkSpace addMember(AddMemberRequest member) {
        // 멤버의 이름과 오너만 매개변수로 넣어서 리턴
        WorkSpace workSpace = workSpaceRepository.findByNameAndOwner(member.getWorkSpaceName(), member.getOwner());
        if (workSpace == null) {
            throw new IllegalArgumentException("Can't find workspace");
        }
        return workSpaceRepository.updateMemberIdsByNameAndOwner(member.getWorkSpaceName(), member.getOwner(), member.getMemberName());
    }

}
