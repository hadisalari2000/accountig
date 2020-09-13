package com.salari.accounting.controller;

import com.salari.accounting.model.domain.AccountAddRequest;
import com.salari.accounting.service.AccountService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/apis")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/v1/account")
    public ResponseEntity<?> addAccount(@Valid @RequestBody AccountAddRequest request){
        return new ResponseEntity<>(accountService.addAccount(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/v1/account")
    public ResponseEntity<?> deleteAccount(@Valid @ApiParam(name="accountId",value="accountId") @RequestParam Long accountId){
        return new ResponseEntity<>(accountService.deleteAccount(accountId),HttpStatus.OK);
    }

    @GetMapping("/v1/accounts/user")
    public ResponseEntity<?> getUserAccounts(@Valid @ApiParam(name="userId",value = "userId") @RequestParam Integer userId,
                                             @Valid @ApiParam(name="pageNumber",value = "pageNumber") @RequestParam Short pageNumber){
        return new ResponseEntity<>(accountService.getAllUserAccounts(userId,pageNumber),HttpStatus.OK);
    }

    @PutMapping("/v1/account/active")
    public ResponseEntity<?> activeAccount(@Valid @ApiParam(name="accountId",value = "accountId") @RequestParam Long accountId){
        return new ResponseEntity<>(accountService.activeAccount(accountId,10),HttpStatus.OK);
    }

    @PutMapping("/v1/account/deActive")
    public ResponseEntity<?> deActiveAccount(@Valid @ApiParam(name="accountId",value = "accountId") @RequestParam Long accountId){
        return new ResponseEntity<>(accountService.deActiveAccount(accountId,10),HttpStatus.OK);
    }
}
