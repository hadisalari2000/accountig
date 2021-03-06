package com.salari.accounting.service;

import com.salari.accounting.model.domain.TransactionAddRequest;
import com.salari.accounting.model.dto.BaseDTO;
import com.salari.accounting.model.dto.MetaDTO;
import com.salari.accounting.model.dto.PagerDTO;
import com.salari.accounting.model.dto.TransactionDTO;
import com.salari.accounting.model.entity.Account;
import com.salari.accounting.model.entity.Transaction;
import com.salari.accounting.model.mapper.TransactionMapper;
import com.salari.accounting.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.salari.accounting.service.GlobalService.getAccountExists;
import static com.salari.accounting.service.GlobalService.serviceExceptionBuilder;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public BaseDTO getAccountTransaction(Long accountId, Short pageNumber) {
        Account account = getAccountExists(accountId);
        if (!account.getIsActive()) return BaseDTO.builder().metaDTO(MetaDTO.getInstance()).data(null).build();

        Pageable pageable = PageRequest.of(pageNumber-1, 10);
        Page<Transaction> transactions = transactionRepository.findTransactionsBySourceAccountId(account.getId(), pageable);
        Page<TransactionDTO> transactionDTOS = transactions.map(transactionMapper::TRANSACTION_DTO);
        PagerDTO<TransactionDTO> pagerDTO = new PagerDTO<>(transactionDTOS);
        return BaseDTO.builder()
                .metaDTO(MetaDTO.getInstance())
                .data(pagerDTO)
                .build();
    }

    public BaseDTO getAccountTransactionForAdmin(Long accountId, Short pageNumber) {
        Account account = getAccountExists(accountId);
        Pageable pageable = PageRequest.of(pageNumber-1, 10);
        Page<Transaction> transactions = transactionRepository.findTransactionsBySourceAccountId(account.getId(), pageable);
        Page<TransactionDTO> transactionDTOS = transactions.map(transactionMapper::TRANSACTION_DTO);
        PagerDTO<TransactionDTO> pagerDTO = new PagerDTO<>(transactionDTOS);
        return BaseDTO.builder()
                .metaDTO(MetaDTO.getInstance())
                .data(pagerDTO)
                .build();
    }

    private Transaction addTransaction(TransactionAddRequest request){
        Transaction transaction=Transaction.builder()
                .transactionDate(request.getTransactionDate())
                .description(request.getDescription())
                .destinationAccountId(request.getDestinationAccountId())
                .operationType(request.getOperationType())
                .remaining(request.getRemaining())
                .sourceAccountId(request.getSourceAccountId())
                .transmitterUserId(request.getTransmitterUserId())
                .build();

       return transactionRepository.save(transaction);
    }
}
