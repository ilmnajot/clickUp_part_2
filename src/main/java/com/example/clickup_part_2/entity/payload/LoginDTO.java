package com.example.clickup_part_2.entity.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class  LoginDTO {

    @NotNull(message = "email cannot be blank")
    private String email;
    @NotNull(message = "password cannot be blank")
    private String password;
}
