package com.example.clickup_part_2.entity;

import com.example.clickup_part_2.entity.templete.AbsLongEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "owner_id"})})
// here is we can manage that the owner is not able to create same named-filed or workspace... demak bu yerda bitta id ikkita workspace yoki manashu coulmn ni yarata olmaydi
public class Workspace extends AbsLongEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String initialLetter;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User owner;

    @ManyToOne(fetch = FetchType.LAZY)
    private Attachment avatar;


    @PrePersist // for compatibility with existing classes
    @PreUpdate// update bo'lishdan oldin update bo'ladigan annotation
    public void setInitialLetter() {
        this.initialLetter = name.substring(0, 1);
    }


    public Workspace(String name, String color, User owner, Attachment avatar) {
        this.name = name;
        this.color = color;
        this.owner = owner;
        this.avatar = avatar;
    }
}
