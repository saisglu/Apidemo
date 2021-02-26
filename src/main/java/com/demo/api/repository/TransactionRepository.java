package com.demo.api.repository;

import com.demo.api.models.Transaction;
import com.demo.api.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select sum(amount) from transactions where transaction_type = 'DEPOSIT' and account_number = ?1",
            nativeQuery = true)
    Optional<Double> getAccountDepositBalance(String accountNumber);

    @Query(value = "select sum(amount) from transactions where transaction_type = 'WITHDRAW' and account_number = ?1",
            nativeQuery = true)
    Optional<Double> getAccountWithdrawBalance(String accountNumber);

    @Query(value = "select * from transactions where account_number = ?1 and transaction_timestamp > ?2 " +
            "and transaction_timestamp < ?3", nativeQuery = true)
    List<Transaction> getTransactions(String accountNumber, Date start, Date end);

    @Query(value = "select * from transactions where account_number = ?1 and transaction_timestamp > ?2 " +
            "and transaction_timestamp < ?3 and transaction_type = ?4", nativeQuery = true)
    List<Transaction> getTransactionsByType(String accountNumber, Date start, Date end, String transactionType);
}
