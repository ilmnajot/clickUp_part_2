package com.example.clickup_part_2.entity.payload;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceDTO {
    @NotEmpty(message="name cannot be empty")
    private String name;

    @NotEmpty(message = "the color cannot be empty")
    private String color;

    private UUID avatarId;
}
