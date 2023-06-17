package com.example.clickup_part_2.entity.enums;

import lombok.ToString;

import java.util.Arrays;
import java.util.List;
public enum WorkspacePermissionName {
    CAN_ADD_MEMBER("Add/remove member",
            "Gives the user the permission to add or remove to the Workspace",
            Arrays.asList(WorkspaceRoleName.ROLE_ADMIN, WorkspaceRoleName.ROLE_OWNER)),
    CAN_EDIT_WORKSPACE("CAN_EDIT_WORKSPACE",
            "Gives the user to remove members to the Workspace",
            Arrays.asList(WorkspaceRoleName.ROLE_ADMIN, WorkspaceRoleName.ROLE_GUEST)),
    CAN_ADD_GUEST("CAN_ADD_GUEST",
            "Gives the user to member to the Workspace",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER, WorkspaceRoleName.ROLE_MEMBER)),
    CAN_SEE_TIME_ESTIMATED("CAN_SEE_TIME_ESTIMATED",
            "Gives the user to member to the Workspace",
            Arrays.asList(WorkspaceRoleName.ROLE_OWNER, WorkspaceRoleName.ROLE_ADMIN));

    private String name;
    private String description;
    private List<WorkspaceRoleName> workspaceRoleNames;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<WorkspaceRoleName> getWorkspaceRoleNames() {
        return workspaceRoleNames;
    }

    WorkspacePermissionName(String name, String description, List<WorkspaceRoleName> workspaceRoleNames) {
        this.description = description;
        this.name  = name;
        this.workspaceRoleNames = workspaceRoleNames;
    }
}

