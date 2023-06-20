package com.example.clickup_part_2.entity;
import com.example.clickup_part_2.entity.enums.SystemRoleName;
import com.example.clickup_part_2.entity.templete.AbsUUIDEntity;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="space")
public class Space extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String initialLetter;

    @ManyToOne(fetch= FetchType.LAZY)
    private Workspace workspace;

    @OneToOne(fetch = FetchType.LAZY)
    private Icons icon;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment attachment;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private SystemRoleName accessType;


}
