package com.example.clickup_part_2.repository;

import com.example.clickup_part_2.entity.WorkspaceUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WorkspaceUserRepository extends JpaRepository<WorkspaceUser, UUID> {

    Optional<WorkspaceUser> findByWorkspaceIdAndUserId(Long workspace_id,  UUID user_id);


    @Transactional //xatolik qaytarib qolishlari mumkin
    @Modifying //bizga xatolikni keragi yo'q, hech narsa qaytarmasin
    void deleteByWorkspaceIdAndUserId(Long workspace_id, UUID user_id);
}
