package com.capstone.crmproject.testfile.repository;

import com.capstone.crmproject.testfile.Company;
import com.capstone.crmproject.testfile.WorkSpace;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class CustomWorkSpaceRepositoryImpl implements CustomWorkSpaceRepository {
    private final MongoTemplate mongoTemplate;

    public CustomWorkSpaceRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public WorkSpace addMemberIdByNameAndOwner(String name, String owner, String memberId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name).and("owner").is(owner));

        Update update = new Update();
        update.push("memberIds", memberId);

        mongoTemplate.updateFirst(query, update, WorkSpace.class);
        return mongoTemplate.findOne(query, WorkSpace.class);
    }

    @Override
    public WorkSpace addCompanyByNameAndOwner(String name, String owner, Company company) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name).and("owner").is(owner));

        Update update = new Update();
        update.push("companies", company);

        mongoTemplate.updateFirst(query, update, WorkSpace.class);
        return mongoTemplate.findOne(query, WorkSpace.class);
    }

    @Override
    public WorkSpace updateCompanyByNameAndOwner(String name, String owner, Company company, String newCompanyName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name).and("owner").is(owner).and("companies.name").is(company.getName()));

        Update update = new Update();
        update.set("companies.$.name", newCompanyName);

        mongoTemplate.updateFirst(query, update, WorkSpace.class);
        return mongoTemplate.findOne(query, WorkSpace.class);
    }
}
