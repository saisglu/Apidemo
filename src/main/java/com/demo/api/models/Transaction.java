package com.demo.api.models;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="transactions")
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private Timestamp transactionTimestamp;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private double amount;
}