package com.salari.accounting.service;

import com.salari.accounting.model.domain.RoleAddRequest;
import com.salari.accounting.model.dto.BaseDTO;
import com.salari.accounting.model.dto.MetaDTO;
import com.salari.accounting.model.entity.Role;
import com.salari.accounting.model.mapper.RoleMapper;
import com.salari.accounting.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.salari.accounting.service.GlobalService.getRoleExists;
import static com.salari.accounting.service.GlobalService.serviceExceptionBuilder;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public BaseDTO getRoleById(Short id) {
        return BaseDTO.builder()
                .metaDTO(MetaDTO.getInstance())
                .data(roleMapper.ROLE_DTO(GlobalService.getRoleExists(id)))
                .build();
    }

    public BaseDTO getAllRole() {
        List<Role> roles = roleRepository.findAllByIdIsNotNull()
                .orElseThrow(() -> serviceExceptionBuilder("not.found.role", HttpStatus.NOT_FOUND));
        return BaseDTO.builder()
                .metaDTO(MetaDTO.getInstance())
                .data(roles.stream().map(roleMapper::ROLE_DTO).collect(Collectors.toList()))
                .build();
    }

    public BaseDTO addRole(RoleAddRequest roleAddRequest) {
        if (roleRepository.findRoleByTitle(roleAddRequest.getTitle()).isPresent())
            throw serviceExceptionBuilder("duplicate_role", HttpStatus.BAD_REQUEST);

        Role role = Role.builder()
                .title(roleAddRequest.getTitle())
                .build();

        roleRepository.save(role);

        return BaseDTO.builder()
                .metaDTO(MetaDTO.getInstance())
                .data(roleMapper.ROLE_DTO(role))
                .build();
    }

    public BaseDTO deleteRole(Short id){
        Role role=getRoleExists(id);
        roleRepository.delete(role);
        return BaseDTO.builder()
                .metaDTO(MetaDTO.getInstance())
                .data(null)
                .build();
    }

}
