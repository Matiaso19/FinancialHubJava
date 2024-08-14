package com.SoyHenry.FinancialHub.service;

import com.SoyHenry.FinancialHub.entities.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface TransactionService {

    List<Transaction> getAll();
    Optional<Transaction> getById(Long id);
    Transaction create(Transaction transaction);
    void delete(Long id);


}
