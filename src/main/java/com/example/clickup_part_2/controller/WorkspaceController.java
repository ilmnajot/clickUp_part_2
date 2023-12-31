package com.example.clickup_part_2.controller;

import com.example.clickup_part_2.entity.User;
import com.example.clickup_part_2.entity.Workspace;
import com.example.clickup_part_2.entity.annotation.CurrentUser;
import com.example.clickup_part_2.entity.api.ApiResponse;
import com.example.clickup_part_2.entity.payload.MemberDTO;
import com.example.clickup_part_2.entity.payload.WorkspaceDTO;
import com.example.clickup_part_2.service.WorkspaceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspace")
public class WorkspaceController {

    @Autowired
    WorkspaceService workspaceService;

    @PostMapping
    public HttpEntity<?> addWorkspace(
            @Valid
            @RequestBody WorkspaceDTO workspaceDTO,
            @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.addWorkspace(workspaceDTO, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?> updateWorkspace(
            @PathVariable Long id,
            @RequestBody WorkspaceDTO workspaceDTO) {
        ApiResponse apiResponse = workspaceService.updateWorkspace(id, workspaceDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?> deleteWorkspace(@PathVariable Long id) {
        ApiResponse apiResponse = workspaceService.deleteWorkspace(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
    @GetMapping("/{id}")
    public Workspace getWorkspace(@PathVariable Long workspaceId) {
        Workspace workspace = workspaceService.getWorkspace(workspaceId);
        return ResponseEntity.ok(workspace).getBody();
    }
    @GetMapping("/all")
    public HttpEntity<?> getWorkspaces() {
        List<Workspace> workspaces = workspaceService.getWorkspaces();
        return ResponseEntity.ok(workspaces);
    }

    @PutMapping("/changeOwner/{id}")
    public HttpEntity<?> changeOwner(
            @PathVariable Long id,
            @PathVariable UUID ownerId) {
        ApiResponse apiResponse = workspaceService.changeOwner(id, ownerId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/addOrEditOrRemove/{id}")
    public HttpEntity<?> addOrEditOrRemoveMember(
            @PathVariable Long id,
            @RequestBody MemberDTO memberDTO) {
        ApiResponse apiResponse =
                workspaceService.addOrEditOrRemoveMember(id, memberDTO);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/join")
    public HttpEntity<?> joinToWorkspace(
            @RequestParam Long id,
            @CurrentUser User user) {
        ApiResponse apiResponse = workspaceService.joinToWorkspace(id, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
