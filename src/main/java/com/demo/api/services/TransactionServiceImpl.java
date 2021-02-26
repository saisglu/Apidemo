package com.demo.api.services;

import com.demo.api.dto.TransactionDto;
import com.demo.api.models.TransactionType;
import com.demo.api.repository.TransactionRepository;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final ModelMapper mapper;

    @Override
    public TransactionDto getLatestBalanceByAccountNumber(String accountNumber) {
        Optional<Double> depositBalance = transactionRepository.getAccountDepositBalance(accountNumber);
        Optional<Double> withdrawBalance = transactionRepository.getAccountWithdrawBalance(accountNumber);
        TransactionDto dto = new TransactionDto();
        dto.setAccountNumber(accountNumber);
        depositBalance.ifPresent(balance -> dto.setAmount(BigDecimal.valueOf(balance - withdrawBalance.orElse(0.0))
                .setScale(3, RoundingMode.HALF_UP)));
        return dto;
    }

    @Override
    public List<TransactionDto> filter(String accountNumber, String start, String end) {
        Pair<LocalDate, LocalDate> dates = getDates(start, end);
        return transactionRepository.getTransactions(accountNumber, Date.valueOf(dates.getKey()), Date.valueOf(dates.getValue()))
                .stream()
                .map(transaction -> mapper.map(transaction, TransactionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> filterByTransactionType(String accountNumber, String transactionType, String start, String end) {
        Pair<LocalDate, LocalDate> dates = getDates(start, end);
        return transactionRepository.getTransactionsByType(accountNumber, Date.valueOf(dates.getKey()),
                Date.valueOf(dates.getValue()), TransactionType.findByLabel(transactionType).name())
                .stream()
                .map(transaction -> mapper.map(transaction, TransactionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDto> getAll() {
        return transactionRepository.findAll()
                .stream()
                .map(transaction -> mapper.map(transaction, TransactionDto.class))
                .collect(Collectors.toList());
    }

    private Pair<LocalDate, LocalDate> getDates(String start, String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end).plusDays(1);
        return new Pair<>(startDate, endDate);
    }
}
