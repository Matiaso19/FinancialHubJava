package com.SoyHenry.FinancialHub.service;

import com.SoyHenry.FinancialHub.dto.account.AccountDtoRequest;
import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoRequest;
import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoResponse;
import com.SoyHenry.FinancialHub.entities.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface TransactionService {

    List<TransactionDtoResponse> getAll();
    TransactionDtoResponse getById(Long id);
    void create(TransactionDtoRequest transactionDtoRequest);
    void delete(Long id);
    void update(Long id, TransactionDtoRequest transactionDtoRequest);

}
