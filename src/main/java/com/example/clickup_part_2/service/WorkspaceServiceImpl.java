package com.example.clickup_part_2.service;

import com.example.clickup_part_2.entity.*;
import com.example.clickup_part_2.entity.api.ApiResponse;
import com.example.clickup_part_2.entity.enums.RoleType;
import com.example.clickup_part_2.entity.enums.WorkspacePermissionName;
import com.example.clickup_part_2.entity.enums.WorkspaceRoleName;
import com.example.clickup_part_2.entity.payload.MemberDTO;
import com.example.clickup_part_2.entity.payload.WorkspaceDTO;
import com.example.clickup_part_2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    WorkspaceUserRepository workspaceUserRepository;

    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;

    @Autowired
    WorkspacePermissionRepositories workspacePermissionRepositories;

    @Autowired
    UserRepository userRepository;

    @Override
    public ApiResponse addWorkspace(WorkspaceDTO workspaceDTO, User user) {
        if (workspaceRepository.existsByOwnerIdAndName(user.getId(), workspaceDTO.getName())) {
            return new ApiResponse("Workspace already exists", false);
        }
        Workspace workspace = new Workspace(
                workspaceDTO.getName(),
                workspaceDTO.getColor(),
                user,
                workspaceDTO.getAvatarId() == null ? null : attachmentRepository.findById(workspaceDTO.getAvatarId()).orElseThrow(()
                        -> new ResourceAccessException("attachment"))
        );
        workspaceRepository.save(workspace);

        WorkspaceRole ownerRole = workspaceRoleRepository.save(new WorkspaceRole(
                WorkspaceRoleName.ROLE_OWNER.name(),
                WorkspaceRoleName.ROLE_OWNER,
                workspace
        ));
        WorkspaceRole adminRole = workspaceRoleRepository.save(new WorkspaceRole(
                WorkspaceRoleName.ROLE_ADMIN.name(),
                WorkspaceRoleName.ROLE_ADMIN,
                workspace
        ));
        WorkspaceRole guestRole = workspaceRoleRepository.save(new WorkspaceRole(
                WorkspaceRoleName.ROLE_GUEST.name(),
                WorkspaceRoleName.ROLE_GUEST,
                workspace
        ));
        WorkspaceRole memberRole = workspaceRoleRepository.save(new WorkspaceRole(
                WorkspaceRoleName.ROLE_MEMBER.name(),
                WorkspaceRoleName.ROLE_MEMBER,
                workspace
        ));


        // ownerga huquqlar berish

        WorkspacePermissionName[] workspacePermissionNames = WorkspacePermissionName.values();

        List<WorkspacePermission> workspacePermissions = new ArrayList<>();

        for (WorkspacePermissionName workspacePermissionName : workspacePermissionNames) {
            WorkspacePermission permission = new WorkspacePermission(
                    ownerRole,
                    workspacePermissionName
            );
            workspacePermissions.add(permission);
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_ADMIN)) {
                new WorkspacePermission(
                        adminRole,
                        workspacePermissionName
                );
                workspacePermissions.add(permission);
            }
            if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_MEMBER)) {
                new WorkspacePermission(
                        memberRole,
                        workspacePermissionName
                );
                workspacePermissions.add(permission);
            } else if (workspacePermissionName.getWorkspaceRoleNames().contains(WorkspaceRoleName.ROLE_GUEST)) {
                new WorkspacePermission(
                        guestRole,
                        workspacePermissionName
                );
                workspacePermissions.add(permission);
            }
        }
        workspacePermissionRepositories.saveAll(workspacePermissions);

        workspaceUserRepository.save(new WorkspaceUser(
                workspace,
                user,
                ownerRole,
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis())
        ));

        return new ApiResponse("workspace is saved successfully", true);
    }

    @Override
    public ApiResponse updateWorkspace(WorkspaceDTO workspaceDTO) {
        return null;
    }

    @Override
    public ApiResponse deleteWorkspace(Long id) {

        try {
            workspaceRepository.deleteById(id);
            return new ApiResponse("workspace is deleted successfully", true);
        } catch (Exception e) {
            return new ApiResponse("there is no such workspace", false);
        }
    }

    @Override
    public ApiResponse getWorkspace(Long id) {


        return null;
    }

    @Override
    public ApiResponse getWorkspaces() {
        return null;
    }

    @Override
    public ApiResponse changeOwner(Long id, UUID ownerId) {
        return null;
    }

    @Override
    public ApiResponse addOrEditOrRemoveMember(Long id, MemberDTO memberDTO) {
        if (memberDTO.getRoleType().equals(RoleType.ADD)) {
            WorkspaceUser workspaceUser = new WorkspaceUser(
                    workspaceRepository.findById(id).orElseThrow(() -> new ResourceAccessException("id")),
                    userRepository.findById(memberDTO.getId()).orElseThrow(() -> new ResourceAccessException("id")),
                    workspaceRoleRepository.findById(memberDTO.getRoleId()).orElseThrow(() -> new ResourceAccessException("id")),
                    new Timestamp(System.currentTimeMillis()),
                    null

            );
            workspaceUserRepository.save(workspaceUser);
        }
        //TODO:EMAILGA INVITE XABAR YUBORISH
        else if (memberDTO.getRoleType().equals(RoleType.UPDATE)) {
            WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, memberDTO.getId()).orElseGet(WorkspaceUser::new);
            workspaceUser.setWorkspaceRole(workspaceRoleRepository.findById(memberDTO.getRoleId()).orElseThrow(() -> new ResourceAccessException("id")));
            workspaceUserRepository.save(workspaceUser);


        } else if (memberDTO.getRoleType().equals(RoleType.DELETE)) {

            workspaceUserRepository.deleteByWorkspaceIdAndUserId(id, memberDTO.getId());

        }
        return new ApiResponse("successfully", true);
    }

    @Override
    public ApiResponse joinToWorkspace(Long id, User user) {
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(id, user.getId());
        try {
            if (optionalWorkspaceUser.isPresent()) {
                WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
                workspaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
                workspaceUserRepository.save(workspaceUser);
                return new ApiResponse("joined successfully", true);
            }
        } catch (Exception e) {
            return new ApiResponse("failed to join", false);
        }
        return new ApiResponse("failed", false);
//            Optional<WorkspaceUser> byWorkspaceIdAndUserId = workspaceUserRepository.findByWorkspaceIdAndUserId(id, user.getId());
//        if (byWorkspaceIdAndUserId.isPresent()) {
//            WorkspaceUser workspaceUser = byWorkspaceIdAndUserId.get();
//            workspaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
//            workspaceUserRepository.save(workspaceUser);
//            return new ApiResponse("joined successfully", true);
//        }
//        else{
//            return new ApiResponse("user is not in this workspace", false);
//        }
    }
}
