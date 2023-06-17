package com.example.clickup_part_2.entity.templete;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;
@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbsUUIDEntity extends AbsMainEntity{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2",strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;
}
