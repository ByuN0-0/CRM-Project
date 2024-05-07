package com.capstone.crmproject.testfile.repository;

import com.capstone.crmproject.testfile.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByUserName(String userName);

    User findByUserName(String userName);
}
