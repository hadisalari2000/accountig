package com.salari.accounting.model.entity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "roles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity<Short> {

    @Column(name = "title",nullable = false)
    @Size(min=3,max=100,message = "{length.role.title}")
    private String title;
}
