package com.demo.api.controllers;

import com.demo.api.dto.TransactionDto;
import com.demo.api.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/health")
    public String healthCheck() {
        return "Up & running";
    }

    @GetMapping
    public List<TransactionDto> getAll() {
        return transactionService.getAll();
    }

    @GetMapping("/{account}")
    public List<TransactionDto> filterTransactions(@PathVariable("account") String accountNumber,
                                                   @RequestParam(required = false) String start, @RequestParam(required = false)String end,
                                                   @RequestParam(required = false) String type) {

        return type == null ? transactionService.filter(accountNumber, start, end)
                : transactionService.filterByTransactionType(accountNumber, type, start, end);
    }

    @GetMapping("/{account}/balance")
    public TransactionDto getAccountBalance(@PathVariable("account") String accountNumber) {
        return transactionService.getLatestBalanceByAccountNumber(accountNumber);
    }


}
