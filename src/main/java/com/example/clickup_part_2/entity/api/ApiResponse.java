package com.example.clickup_part_2.entity.api;

import com.example.clickup_part_2.entity.Workspace;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NotNull
public class ApiResponse {
    private String message;
    private  boolean success;

}
