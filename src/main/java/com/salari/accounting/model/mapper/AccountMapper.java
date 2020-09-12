package com.salari.accounting.model.mapper;

import com.salari.accounting.model.dto.AccountDTO;
import com.salari.accounting.model.entity.Account;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {
    AccountDTO ACCOUNT_DTO(Account account);
}
