package com.capstone.crmproject.security;

import com.capstone.crmproject.entity.UserEntity;
import com.capstone.crmproject.repository.UserRepository;
import com.capstone.crmproject.repository.WorkspaceMemberRepository;
import com.capstone.crmproject.repository.WorkspaceRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    public CustomUserDetailsService(UserRepository userRepository, WorkspaceRepository workspaceRepository, WorkspaceMemberRepository workspaceMemberRepository) {
        this.userRepository = userRepository;
        this.workspaceRepository = workspaceRepository;
        this.workspaceMemberRepository = workspaceMemberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userData = userRepository.findByUsername(username);
        // List<WorkspaceMemberEntity> workspaceEntity = workspaceMemberRepository.findByMemberId(userData.getUserId());
        // workSpaceRepository find

        if (userData != null) {
            return new CustomUserDetails(userData);
        }
        System.out.println("User not found with username: " + username);
        return null;
    }
}
