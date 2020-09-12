package com.salari.accounting.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferRequest {
    private Long sourceAccountId;
    private Long destinationAccountId;
    private Long amount;
}
