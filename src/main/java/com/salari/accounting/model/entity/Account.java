package com.salari.accounting.model.entity;

import com.salari.accounting.model.enums.AccountTypes;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="accounts")
public class Account extends BaseEntity<Long> {

    @Column(name="user_id",nullable = false)
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "user_id",insertable = false,updatable = false)
    private User user;

    @Column(name = "account_type",length = 20,nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountTypes accountType;

    @NotNull
    private Long openingDate;

    @Column(name="account_number",nullable = false,unique = true)
    @Size(min=10,max=30)
    private String accountNumber;

    @NotNull
    @Min(0)
    private Long remaining;

    @NotNull
    private Boolean isActive;
}
