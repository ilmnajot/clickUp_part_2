package com.example.clickup_part_2.service;

import com.example.clickup_part_2.entity.User;
import com.example.clickup_part_2.entity.Workspace;
import com.example.clickup_part_2.entity.api.ApiResponse;
import com.example.clickup_part_2.entity.payload.MemberDTO;
import com.example.clickup_part_2.entity.payload.WorkspaceDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface WorkspaceService { // aslida logikalarni service ni o'zida yozish xato xisoblanmaydi ammo noqulaylik tug'dirmasligi uchun biz birinchi rejasini interface da yozib olamiz.

    ApiResponse addWorkspace(WorkspaceDTO workspaceDTO, User user);

    ApiResponse updateWorkspace(Long id, WorkspaceDTO workspaceDTO);

    ApiResponse deleteWorkspace(Long id);

    Workspace getWorkspace(Long workspaceId);

    List<Workspace> getWorkspaces();

    ApiResponse changeOwner(Long id, UUID ownerId);

    ApiResponse addOrEditOrRemoveMember(Long id, MemberDTO memberId);

    ApiResponse joinToWorkspace(Long id, User user);
}
