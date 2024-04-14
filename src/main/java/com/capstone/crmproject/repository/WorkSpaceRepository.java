package com.capstone.crmproject.repository;

import com.capstone.crmproject.model.WorkSpace;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkSpaceRepository extends MongoRepository<WorkSpace, String> {
    WorkSpace findByNameAndOwner(String name, String owner);
}
