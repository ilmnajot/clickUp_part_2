package com.example.clickup_part_2.repository;

import com.example.clickup_part_2.entity.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    boolean existsByOwnerIdAndName(UUID ownerId, String name);
    boolean findByNameAndId(String name, Long id);

}
