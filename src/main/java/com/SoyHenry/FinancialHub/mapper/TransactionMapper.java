package com.SoyHenry.FinancialHub.mapper;

import com.SoyHenry.FinancialHub.dto.account.AccountDtoRequest;
import com.SoyHenry.FinancialHub.dto.account.AccountDtoResponse;
import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoRequest;
import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoResponse;
import com.SoyHenry.FinancialHub.entities.Account;
import com.SoyHenry.FinancialHub.entities.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    TransactionDtoRequest mapToDtoRequest(Transaction transaction);
    TransactionDtoResponse mapToDtoResponse(Transaction transaction);
    Transaction mapToTransaction(TransactionDtoRequest transactionDtoRequest);
}
