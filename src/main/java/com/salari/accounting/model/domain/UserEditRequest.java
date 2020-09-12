package com.salari.accounting.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEditRequest {

    @NotNull
    private Integer id;

    @NotNull
    @Size(min = 3, max = 20, message = "{length.user.first.name}")
    private String firstName;

    @NotNull
    @Size(min = 3, max = 30, message = "{length.user.last.name}")
    private String lastName;

    @NotNull
    @Size(min = 10, max = 11, message = "{length.user.national.code}")
    @Pattern(regexp = "regexp.numbers", message = "{invalid.user.national.code}")
    private String nationalCode;

    @NotNull
    private Short roleId;
}
