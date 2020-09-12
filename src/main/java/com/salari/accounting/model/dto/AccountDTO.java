package com.salari.accounting.model.dto;

import com.salari.accounting.model.enums.AccountTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

    private Long id;
    private Integer userId;
    private UserDTO user;
    private AccountTypes accountType;
    private Long openingDate;
    private String accountNumber;
    private Long remaining;
}
