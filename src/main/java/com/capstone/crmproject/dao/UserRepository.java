package com.capstone.crmproject.dao;

import com.capstone.crmproject.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
