package com.SoyHenry.FinancialHub.service;

import com.SoyHenry.FinancialHub.entities.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class TransactionServiceImp implements TransactionService {

    List<Transaction> transactions = new ArrayList<>();


    @Override
    public List<Transaction> getAll() {
        return transactions;
    }

    @Override
    public Optional<Transaction> getById(Long id) {
        return transactions.stream()
                .filter(transaction -> transaction.getId().equals(id))
                .findFirst();
    }

    @Override
    public Transaction create(Transaction transaction) {
        transactions.add(transaction);
        return transaction;
    }

    @Override
    public void delete(Long id) {
        transactions.removeIf(transaction -> transaction.getId().equals(id));
    }
}
