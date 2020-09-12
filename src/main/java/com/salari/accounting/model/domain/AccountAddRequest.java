package com.salari.accounting.model.domain;

import com.salari.accounting.model.enums.AccountTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountAddRequest {

    @NotNull
    private Integer userId;

    @NotNull
    private AccountTypes accountType;

    @NotNull
    private Long openingDate;

    @NotNull
    private Long remaining;
}
