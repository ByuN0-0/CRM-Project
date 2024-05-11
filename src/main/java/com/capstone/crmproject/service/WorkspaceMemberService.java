package com.capstone.crmproject.service;

import com.capstone.crmproject.entity.WorkspaceEntity;
import com.capstone.crmproject.entity.WorkspaceMemberEntity;
import com.capstone.crmproject.repository.WorkspaceMemberRepository;
import com.capstone.crmproject.repository.WorkspaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class WorkspaceMemberService {
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceRepository workSpaceRepository;

    public WorkspaceMemberService(WorkspaceMemberRepository workspaceMemberRepository, WorkspaceRepository workSpaceRepository) {
        this.workspaceMemberRepository = workspaceMemberRepository;
        this.workSpaceRepository = workSpaceRepository;
    }

    @Transactional
    public List<WorkspaceMemberEntity> getMemberList(UUID workspaceId) {
        // 멤버의 이름과 오너만 매개변수로 넣어서 리턴
        List<WorkspaceMemberEntity> memberList = workspaceMemberRepository.findByWorkspaceId(workspaceId);

        if (memberList == null) {
            throw new IllegalArgumentException("Can't find workspace member");
        }
        return memberList;
    }

    @Transactional
    public List<WorkspaceMemberEntity> getWorkspaceList(String memberId) {
        // 멤버의 이름과 오너만 매개변수로 넣어서 리턴
        List<WorkspaceMemberEntity> workspaceList = workspaceMemberRepository.findByMemberId(memberId);

        if (workspaceList == null) {
            throw new IllegalArgumentException("Can't find workspace");
        }
        return workspaceList;
    }

    @Transactional
    public WorkspaceMemberEntity addMember(UUID workspaceId, String memberId) {
        // 멤버의 이름과 오너만 매개변수로 넣어서 리턴
        WorkspaceEntity workspaceEntity = workSpaceRepository.findByWorkspaceId(workspaceId);
        if (workspaceEntity == null) {
            throw new IllegalArgumentException("Can't find workspace");
        }
        WorkspaceMemberEntity workspaceMember = new WorkspaceMemberEntity();
        workspaceMember.setWorkspaceId(workspaceId);
        workspaceMember.setMemberId(memberId);
        if (workspaceMemberRepository.findByWorkspaceIdAndMemberId(workspaceId, memberId) != null) {
            throw new IllegalArgumentException("Member already exists");
        }
        return workspaceMemberRepository.save(workspaceMember);
    }

    @Transactional
    public WorkspaceMemberEntity removeMember(UUID workspaceId, String memberId) {
        // 멤버의 이름과 오너만 매개변수로 넣어서 리턴
        WorkspaceEntity workSpaceEntity = workSpaceRepository.findByWorkspaceId(workspaceId);

        if (workSpaceEntity == null) {
            throw new IllegalArgumentException("Can't find workspace");
        }
        WorkspaceMemberEntity workSpaceMember = workspaceMemberRepository.findByWorkspaceIdAndMemberId(workspaceId, memberId);
        if (workSpaceMember == null) {
            throw new IllegalArgumentException("Can't find workspace member");
        }
        workspaceMemberRepository.delete(workSpaceMember);
        return workspaceMemberRepository.save(workSpaceMember);
    }

    public boolean isMember(UUID workspaceId, String username) {
        return !workspaceMemberRepository.existsByWorkspaceIdAndMemberId(workspaceId, username);
    }
}
