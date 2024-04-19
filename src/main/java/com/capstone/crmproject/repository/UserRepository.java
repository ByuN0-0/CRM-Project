package com.capstone.crmproject.repository;

import com.capstone.crmproject.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUserName(String userName);
    User findByUserName(String userName);
}
