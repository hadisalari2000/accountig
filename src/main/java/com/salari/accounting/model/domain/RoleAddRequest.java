package com.salari.accounting.model.domain;

import com.salari.accounting.model.enums.RoleTypes;
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
public class RoleAddRequest {

    @NotNull
    @Size(min=3,max=100,message = "{length.role.title}")
    private String title;

    private RoleTypes roleTypes;
}
