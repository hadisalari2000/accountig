package com.salari.accounting.controller;

import com.salari.accounting.model.domain.RoleAddRequest;
import com.salari.accounting.model.enums.RoleTypes;
import com.salari.accounting.service.RoleService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/apis")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/v1/role")
    public ResponseEntity<?> getRoleById(@Valid @ApiParam(name = "roleId",value = "roleId") @RequestParam Short roleId){
        return new ResponseEntity<>(roleService.getRoleById(roleId), HttpStatus.OK);
    }

    @GetMapping("/v1/role/roleType")
    public ResponseEntity<?> getRoleByRoleType(@Valid @ApiParam(name = "roleTypes",value = "roleTypes") @RequestParam RoleTypes roleTypes){
        return new ResponseEntity<>(roleService.getRoleByRoleType(roleTypes), HttpStatus.OK);
    }

    @GetMapping("/v1/roles")
    public ResponseEntity<?> getAllRole(){
        return new ResponseEntity<>(roleService.getAllRole(), HttpStatus.OK);
    }

    @PostMapping("/v1/role")
    public ResponseEntity<?> addRole(@Valid @RequestBody RoleAddRequest roleAddRequest){
        return new ResponseEntity<>(roleService.addRole(roleAddRequest),HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/role")
    public ResponseEntity<?> deleteRole(@Valid @ApiParam(value = "roleId",name="roleId") @RequestParam Short roleId){
        return new ResponseEntity<>(roleService.deleteRole(roleId),HttpStatus.OK);
    }
}
