package com.example.clickup_part_2.entity;

import com.example.clickup_part_2.entity.enums.WorkspaceRoleName;
import com.example.clickup_part_2.entity.templete.AbsUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WorkspaceRole extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private WorkspaceRoleName extendsRole;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Workspace workspace;

}
