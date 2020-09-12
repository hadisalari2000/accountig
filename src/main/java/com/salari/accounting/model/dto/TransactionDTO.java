package com.salari.accounting.model.dto;

import com.salari.accounting.model.enums.OperationTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private AccountDTO sourceAccount;
    private AccountDTO destinationAccount;
    private Long transactionDate=System.currentTimeMillis();
    private Long remaining;
    private OperationTypes operationType;
    private Integer transmitterUserId;
    private UserDTO transmitterUser;
    private String description;
}
