package com.salari.accounting.repository;

import com.salari.accounting.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Integer>, JpaSpecificationExecutor<Account> {

    Optional<Account> findAccountById(Integer id);
    Optional<Account> findAccountByAccountNumber(String accountNumber);
}
