package com.salari.accounting.model.domain;

import com.salari.accounting.model.enums.OperationTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionAddRequest {
    private Long sourceAccountId;
    private Long destinationAccountId;
    private Long transactionDate=System.currentTimeMillis();
    private Long remaining;
    private OperationTypes operationType;
    private Integer transmitterUserId;
    private String description;
}
