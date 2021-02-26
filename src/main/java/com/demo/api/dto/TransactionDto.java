package com.demo.api.dto;

import com.demo.api.models.TransactionType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto {
    private String accountNumber;
    private Timestamp transactionTimestamp;
    private TransactionType transactionType;
    private BigDecimal amount = new BigDecimal(0);
}