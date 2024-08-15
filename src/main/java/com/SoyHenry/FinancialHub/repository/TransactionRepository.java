package com.SoyHenry.FinancialHub.repository;

import com.SoyHenry.FinancialHub.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
