package com.SoyHenry.FinancialHub.service.impl;

import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoRequest;
import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoResponse;
import com.SoyHenry.FinancialHub.entities.Transaction;
import com.SoyHenry.FinancialHub.mapper.TransactionMapper;
import com.SoyHenry.FinancialHub.repository.TransactionRepository;
import com.SoyHenry.FinancialHub.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<TransactionDtoResponse> getAll() {
        List<Transaction> transactions = transactionRepository.findAll();
        return mapToDtoList(transactions);
    }

    @Override
    public TransactionDtoResponse getById(Long id) {
        Optional<Transaction> transaction = transactionRepository.findById(id);
        return transaction.map(transactionMapper::mapToDtoResponse).orElse(null);
    }

    @Override
    public void create(TransactionDtoRequest transactionDtoRequest) {
        Transaction transaction = transactionMapper.mapToTransaction(transactionDtoRequest);
        transactionRepository.save(transaction);
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public void update(Long id, TransactionDtoRequest transactionDtoRequest) {
        Optional<Transaction> updatedTransaction = transactionRepository.findById(id);
        if(updatedTransaction.isPresent()){
            Transaction transaction = updatedTransaction.get();
            transaction.setAmount(transactionDtoRequest.getAmount());
            transaction.setDate(transactionDtoRequest.getDate());
            transaction.setType(transactionDtoRequest.getType());
            transactionRepository.save(transaction);
        }
    }

    private List<TransactionDtoResponse> mapToDtoList(List<Transaction> transactions){
        return transactions.stream()
                .map(transactionMapper::mapToDtoResponse)
                .collect(Collectors.toList());
    }
}
