package com.salari.accounting.service;

import com.salari.accounting.exception.ServiceException;
import com.salari.accounting.model.entity.Account;
import com.salari.accounting.model.entity.Role;
import com.salari.accounting.model.entity.User;
import com.salari.accounting.model.enums.RoleTypes;
import com.salari.accounting.repository.AccountRepository;
import com.salari.accounting.repository.RoleRepository;
import com.salari.accounting.repository.UserRepository;
import lombok.Synchronized;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class GlobalService {

    private static RoleRepository roleRepository;
    private static UserRepository userRepository;
    private static AccountRepository accountRepository;

    public GlobalService(RoleRepository roleRepository,UserRepository userRepository,AccountRepository accountRepository) {
        GlobalService.roleRepository=roleRepository;
        GlobalService.userRepository=userRepository;
        GlobalService.accountRepository=accountRepository;
    }

    @Synchronized
    public static ServiceException serviceExceptionBuilder(String key, HttpStatus httpStatus) {
        return ServiceException.builder()
                .key(key)
                .message(key)
                .httpStatus(httpStatus)
                .build();
    }

    @Synchronized
    public static Role getRoleExists(Short roleId){
       return roleRepository.findRoleById(roleId)
                .orElseThrow(()->serviceExceptionBuilder("not.found.role",HttpStatus.NOT_FOUND));
    }

    @Synchronized
    public static Role getRoleExists(RoleTypes roleTypes){
        return roleRepository.findRoleByRoleTypes(roleTypes)
                .orElseThrow(()->serviceExceptionBuilder("not.found.role",HttpStatus.NOT_FOUND));
    }

    @Synchronized
    public static User getUserExists(Integer userId){
        return userRepository.findUserById(userId)
                .orElseThrow(()->serviceExceptionBuilder("not.found.user",HttpStatus.NOT_FOUND));
    }

    @Synchronized
    public static User getUserExists(String userName){
        return userRepository.findUserByUserName(userName)
                .orElseThrow(()->serviceExceptionBuilder("not.found.user",HttpStatus.NOT_FOUND));
    }

    @Synchronized
    public static Account getAccountExists(Integer accountId){
        return accountRepository.findAccountById(accountId)
                .orElseThrow(()->serviceExceptionBuilder("not.found.account",HttpStatus.NOT_FOUND));
    }

    @Synchronized
    public static Account getAccountExists(String accountNumber){
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(()->serviceExceptionBuilder("not.found.account",HttpStatus.NOT_FOUND));
    }
}
