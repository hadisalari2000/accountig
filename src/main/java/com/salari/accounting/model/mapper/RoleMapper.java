package com.salari.accounting.model.mapper;

import com.salari.accounting.model.dto.RoleDTO;
import com.salari.accounting.model.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleDTO ROLE_DTO(Role role);
}
