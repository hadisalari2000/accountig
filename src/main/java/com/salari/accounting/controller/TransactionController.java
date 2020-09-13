package com.salari.accounting.controller;

import com.salari.accounting.service.TransactionService;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/apis")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/v1/transactions/account")
    public ResponseEntity<?> getAccountTransaction(
            @Valid @ApiParam(name="accountId",value="accountId") @RequestParam Long accountId,
            @Valid @ApiParam(name="pageNumber",value="pageNumber") @RequestParam Short pageNumber){
        return new ResponseEntity<>(transactionService.getAccountTransaction(accountId,pageNumber), HttpStatus.OK);
    }

    @GetMapping("/v1/transactions/account/admin")
    public ResponseEntity<?> getAccountTransactionForAdmin(
            @Valid @ApiParam(name="accountId",value="accountId") @RequestParam Long accountId,
            @Valid @ApiParam(name="pageNumber",value="pageNumber") @RequestParam Short pageNumber){
        return new ResponseEntity<>(transactionService.getAccountTransactionForAdmin(accountId,pageNumber), HttpStatus.OK);
    }
}
