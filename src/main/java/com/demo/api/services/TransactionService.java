package com.demo.api.services;

import com.demo.api.dto.TransactionDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {
    TransactionDto getLatestBalanceByAccountNumber(String accountNumber);
    List<TransactionDto> filter(String accountNumber, String start, String end);
    List<TransactionDto> filterByTransactionType(String accountNumber, String transactionType, String start,
                                                 String end);
    List<TransactionDto> getAll();
}
