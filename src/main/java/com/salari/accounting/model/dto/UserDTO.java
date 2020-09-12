package com.salari.accounting.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String nationalCode;
    private Short roleId;
    private RoleDTO role;
    private List<AccountDTO> accounts;
}
