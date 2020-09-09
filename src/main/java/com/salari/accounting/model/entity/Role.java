package com.salari.accounting.model.entity;
import com.salari.accounting.model.enums.RoleTypes;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
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
    @Pattern(regexp = "regexp.letters",message = "invalid.role.title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name="key",nullable = false)
    @Size(max=5)
    private RoleTypes roleTypes;
}
