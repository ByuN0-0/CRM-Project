package com.capstone.crmproject.repository;

import com.capstone.crmproject.model.WorkSpace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface WorkSpaceRepository extends MongoRepository<WorkSpace, String>, CustomWorkSpaceRepository {
    WorkSpace findByNameAndOwner(String name, String owner);
}
