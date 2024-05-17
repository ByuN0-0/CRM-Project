package com.capstone.crmproject.testfile.repository;

import com.capstone.crmproject.testfile.WorkSpace;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WorkSpaceRepository extends MongoRepository<WorkSpace, String>, CustomWorkSpaceRepository {
    WorkSpace findByNameAndOwner(String name, String owner);
}
