package com.salari.accounting.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserChangePasswordRequest {

    @NotNull
    private Integer id;

    @NotNull
    @Size(min=5,max=20)
    private String currentPassword;

    @NotNull
    @Size(min=5,max=20)
    private String newPassword;
}
