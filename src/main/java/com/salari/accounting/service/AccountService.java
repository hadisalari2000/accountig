package com.salari.accounting.service;

import com.salari.accounting.model.domain.AccountAddRequest;
import com.salari.accounting.model.dto.AccountDTO;
import com.salari.accounting.model.dto.BaseDTO;
import com.salari.accounting.model.dto.MetaDTO;
import com.salari.accounting.model.dto.PagerDTO;
import com.salari.accounting.model.entity.Account;
import com.salari.accounting.model.entity.User;
import com.salari.accounting.model.mapper.AccountMapper;
import com.salari.accounting.repository.AccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.salari.accounting.service.GlobalService.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public BaseDTO getAccountById(Long id) {
        return BaseDTO.builder()
                .metaDTO(MetaDTO.getInstance())
                .data(accountMapper.ACCOUNT_DTO(GlobalService.getAccountExists(id)))
                .build();
    }

    public BaseDTO getAccountByAccountNumber(String accountNumber) {
        return BaseDTO.builder()
                .metaDTO(MetaDTO.getInstance())
                .data(accountMapper.ACCOUNT_DTO(GlobalService.getAccountExists(accountNumber)))
                .build();
    }

    public BaseDTO getAllUserAccounts(Short pageNumber) {
        String username=getCurrentUsername();
        User user=getUserExists(username);
        Pageable pageable = PageRequest.of(pageNumber-1, 10);
        Page<Account> accounts = accountRepository.findAccountsByUserId(user.getId(),pageable);
        Page<AccountDTO> accountDTOS = accounts.map(accountMapper::ACCOUNT_DTO);
        PagerDTO<AccountDTO> pagerDTO = new PagerDTO<>(accountDTOS);
        return BaseDTO.builder()
                .metaDTO(MetaDTO.getInstance())
                .data(pagerDTO)
                .build();
    }

    public BaseDTO getAllUserAccountsForAdmin(Integer userId,Short pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber-1, 10);
        Page<Account> accounts = accountRepository.findAccountsByUserId(userId,pageable);
        Page<AccountDTO> accountDTOS = accounts.map(accountMapper::ACCOUNT_DTO);
        PagerDTO<AccountDTO> pagerDTO = new PagerDTO<>(accountDTOS);
        return BaseDTO.builder()
                .metaDTO(MetaDTO.getInstance())
                .data(pagerDTO)
                .build();
    }

    @Transactional
    public BaseDTO addAccount(AccountAddRequest request) {
        String accountNumber=createAccountNumber();

        User user=getUserExists(request.getUserId());
        if (accountRepository.findAccountByAccountNumber(accountNumber).isPresent())
            throw serviceExceptionBuilder("duplicate.account.number", HttpStatus.BAD_REQUEST);

        if(accountRepository.findAccountByUserIdAndAccountType(user.getId(),request.getAccountType()).isPresent())
            throw serviceExceptionBuilder("duplicate.account.type", HttpStatus.BAD_REQUEST);

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .accountType(request.getAccountType())
                .openingDate(request.getOpeningDate())
                .remaining(request.getRemaining())
                .userId(user.getId())
                .isActive(true)
                .build();

        accountRepository.save(account);

        return BaseDTO.builder()
                .metaDTO(MetaDTO.getInstance())
                .data(accountMapper.ACCOUNT_DTO(account))
                .build();
    }

    public BaseDTO deleteAccount(Long id){
        Account account=getAccountExists(id);
        accountRepository.delete(account);
        return BaseDTO.builder()
                .metaDTO(MetaDTO.getInstance())
                .data(null)
                .build();
    }

    public BaseDTO activeAccount(Long accountId, Integer userId) {
        Account account=accountRepository.findAccountByIdAndUserId(accountId,userId)
                .orElseThrow(()->serviceExceptionBuilder("not.found.account",HttpStatus.NOT_FOUND));

        account.setIsActive(true);
        accountRepository.save(account);
        return BaseDTO.builder()
                .metaDTO(MetaDTO.getInstance())
                .data(accountMapper.ACCOUNT_DTO(account))
                .build();
    }

    public BaseDTO deActiveAccount(Long accountId, Integer userId) {
        Account account=accountRepository.findAccountByIdAndUserId(accountId,userId)
                .orElseThrow(()->serviceExceptionBuilder("not.found.account",HttpStatus.NOT_FOUND));

        account.setIsActive(false);
        accountRepository.save(account);
        return BaseDTO.builder()
                .metaDTO(MetaDTO.getInstance())
                .data(accountMapper.ACCOUNT_DTO(account))
                .build();
    }

    private String createAccountNumber(){
        Optional<String> account=accountRepository.findMaxByAccountNumber();
        return String.format("%010d", Long.parseLong(account.isPresent()?account.get():"0")+1);
    }
}
