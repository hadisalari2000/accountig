package com.salari.accounting.model.dto;

import com.salari.accounting.model.enums.RoleTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private Short id;
    private Long creationDate;
    private String title;
    private RoleTypes roleTypes;
}
