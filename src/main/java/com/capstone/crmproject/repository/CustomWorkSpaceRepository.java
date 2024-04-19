package com.capstone.crmproject.repository;

import com.capstone.crmproject.model.WorkSpace;

import java.util.List;

public interface CustomWorkSpaceRepository {
    WorkSpace updateMemberIdsByNameAndOwner(String name, String owner, String memberIds);
}
