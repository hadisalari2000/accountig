package com.salari.accounting.model.entity;

import com.salari.accounting.model.enums.OperationTypes;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity<Long> {

    @Column(name="source_account_id",nullable = true)
    private Long sourceAccountId;

    @Column(name="destination_account_id",nullable = true)
    private Long destinationAccountId;

    @ManyToOne(optional = true,fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "source_account_id",insertable = false,updatable = false)
    private Account sourceAccount;

    @ManyToOne(optional = true,fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name="destination_account_id",insertable = false,updatable = false)
    private Account destinationAccount;

    @NotNull
    private Long transactionDate=System.currentTimeMillis();

    @NotNull
    @Min(0)
    private Long remaining;

    @Column(name="operation_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private OperationTypes operationType;

    @Column(name="transmitter_user_id",nullable = false)
    private Integer transmitterUserId;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "transmitter_user_id",insertable = false,updatable = false)
    private User transmitterUser;

    @Column(name="description",nullable = false)
    @Size(min=3,max=200)
    private String description;
}
