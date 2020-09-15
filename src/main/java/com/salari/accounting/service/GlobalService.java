package com.salari.accounting.service;

import com.salari.accounting.configuration.ApplicationProperties;
import com.salari.accounting.exception.ServiceException;
import com.salari.accounting.model.entity.Account;
import com.salari.accounting.model.entity.Role;
import com.salari.accounting.model.entity.User;
import com.salari.accounting.repository.AccountRepository;
import com.salari.accounting.repository.RoleRepository;
import com.salari.accounting.repository.UserRepository;
import lombok.Synchronized;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
                .key(ApplicationProperties.getProperty(key))
                .message(ApplicationProperties.getProperty(key))
                .httpStatus(httpStatus)
                .build();
    }

    @Synchronized
    public static Role getRoleExists(Short roleId){
       return roleRepository.findRoleById(roleId)
                .orElseThrow(()->serviceExceptionBuilder("not.found.role",HttpStatus.NOT_FOUND));
    }

    @Synchronized
    public static User getUserExists(Integer userId){
        return userRepository.findUserById(userId)
                .orElseThrow(()->serviceExceptionBuilder("not.found.user",HttpStatus.NOT_FOUND));
    }

    @Synchronized
    public static User getUserExists(String userName){
        return userRepository.findUserByUserNameIgnoreCase(userName)
                .orElseThrow(()->serviceExceptionBuilder("not.found.user",HttpStatus.NOT_FOUND));
    }

    @Synchronized
    public static Account getAccountExists(Long accountId){
        return accountRepository.findAccountById(accountId)
                .orElseThrow(()->serviceExceptionBuilder("not.found.account",HttpStatus.NOT_FOUND));
    }

    @Synchronized
    public static Account getAccountExists(String accountNumber){
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(()->serviceExceptionBuilder("not.found.account",HttpStatus.NOT_FOUND));
    }

    @Synchronized public static String getCurrentUsername(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username="";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
           username = principal.toString();
        }
        return username;
    }
}
