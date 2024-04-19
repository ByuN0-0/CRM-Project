package com.capstone.crmproject.repository;

import com.capstone.crmproject.model.WorkSpace;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

public class CustomWorkSpaceRepositoryImpl implements CustomWorkSpaceRepository{
    private final MongoTemplate mongoTemplate;
    public CustomWorkSpaceRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    public WorkSpace updateMemberIdsByNameAndOwner(String name, String owner, String memberId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name).and("owner").is(owner));

        Update update = new Update();
        update.push("memberIds", memberId);

        mongoTemplate.updateFirst(query, update, WorkSpace.class);
        return mongoTemplate.findOne(query, WorkSpace.class);
    }
}
