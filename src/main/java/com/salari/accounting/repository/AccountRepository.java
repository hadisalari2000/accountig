package com.salari.accounting.repository;

import com.salari.accounting.model.entity.Account;
import com.salari.accounting.model.enums.AccountTypes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long>, JpaSpecificationExecutor<Account> {

    Optional<Account> findAccountById(Long id);
    Optional<Account> findAccountByAccountNumber(String accountNumber);
    Optional<Account> findAccountByUserIdAndAccountNumber(Integer userId,String accountNumber);
    Optional<Account> findAccountByIdAndUserId(Long accountId,Integer userId);
    Optional<List<Account>> findAccountsByUserId(Integer userId);
    Optional<Account> findAccountByUserIdAndAccountType(Integer userId, AccountTypes accountTypes);

    @Query(value = "SELECT max(accountNumber) FROM Account ")
    Optional<String> findMaxByAccountNumber();

    Page<Account> findAccountsByUserId(Integer userId, Pageable pageable);
}
