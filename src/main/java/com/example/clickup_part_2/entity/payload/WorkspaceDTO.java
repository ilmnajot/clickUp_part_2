package com.example.clickup_part_2.entity.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor

@NoArgsConstructor
public class WorkspaceDTO {
    @NotNull
    private String name;

    @NotNull
    private String color;

    private UUID avatarId;
}
