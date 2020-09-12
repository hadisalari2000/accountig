package com.salari.accounting.model.mapper;

import com.salari.accounting.model.dto.TransactionDTO;
import com.salari.accounting.model.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionMapper {
    TransactionDTO TRANSACTION_DTO(Transaction transaction);
}
