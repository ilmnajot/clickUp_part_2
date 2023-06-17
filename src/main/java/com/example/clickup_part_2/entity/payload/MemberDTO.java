package com.example.clickup_part_2.entity.payload;
import com.example.clickup_part_2.entity.enums.RoleType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private UUID id;

    private UUID roleId;

    @Enumerated(EnumType.STRING)
    private RoleType roleType; //ADD, UPDATE, DELETE
}
