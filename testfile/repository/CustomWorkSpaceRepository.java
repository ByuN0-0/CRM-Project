package com.capstone.crmproject.testfile.repository;

import com.capstone.crmproject.testfile.Company;
import com.capstone.crmproject.testfile.WorkSpace;

public interface CustomWorkSpaceRepository {
    WorkSpace addMemberIdByNameAndOwner(String name, String owner, String memberIds);

    WorkSpace addCompanyByNameAndOwner(String name, String owner, Company company);

    WorkSpace updateCompanyByNameAndOwner(String name, String owner, Company company, String newCompanyName);
}
