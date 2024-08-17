package com.SoyHenry.FinancialHub.service.impl;

import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoRequest;
import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoResponse;
import com.SoyHenry.FinancialHub.entities.Account;
import com.SoyHenry.FinancialHub.entities.Transaction;
import com.SoyHenry.FinancialHub.mapper.TransactionMapper;
import com.SoyHenry.FinancialHub.repository.AccountRepository;
import com.SoyHenry.FinancialHub.repository.TransactionRepository;
import com.SoyHenry.FinancialHub.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.accountRepository = accountRepository;
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

 //  @Override
//    public void create(TransactionDtoRequest transactionDtoRequest) {
//        Transaction transaction = transactionMapper.mapToTransaction(transactionDtoRequest);
//        transactionRepository.save(transaction);
//    }
    @Transactional
    public void create(TransactionDtoRequest dto) {
        // 1. Buscar la cuenta utilizando el accountId
        Account account = accountRepository.findById(dto.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        // 2. Crear una nueva transacción
        Transaction transaction = new Transaction();
        transaction.setType(dto.getType());
        transaction.setAmount(dto.getAmount());
        transaction.setDate(dto.getDate());
        transaction.setAccount(account);

        // 3. Actualizar el balance de la cuenta
        if ("CREDIT".equalsIgnoreCase(dto.getType())) {
            account.setBalance(account.getBalance() + dto.getAmount());
        } else if ("DEBIT".equalsIgnoreCase(dto.getType())) {
            account.setBalance(account.getBalance() - dto.getAmount());
        } else {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        // 4. Guardar la transacción y la cuenta actualizada
        transactionRepository.save(transaction);
        accountRepository.save(account);


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
