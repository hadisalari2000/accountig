package com.salari.accounting.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "users")
public class User extends BaseEntity<Integer> {

    @Column(name = "user_name", nullable = false, unique = true)
    @Size(min = 4, max = 20, message = "{length.user.username}")
    @Pattern(regexp = "^[0-9A-Za-z]*$", message = "{invalid.user.username}")
    private String userName;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "first_name", nullable = false)
    @Size(min = 3, max = 20, message = "{length.user.first.name}")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(min = 3, max = 30, message = "{length.user.last.name}")
    private String lastName;

    @Column(name = "national_code", nullable = false, unique = true)
    @Size(min = 10, max = 11, message = "{length.user.national.code}")
    @Pattern(regexp = "^[0-9]*$", message = "{invalid.user.national.code}")
    private String nationalCode;

    @Column(name = "role_id", nullable = false)
    private Short roleId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", updatable = false, insertable = false)
    private Role role;

}
