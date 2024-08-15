package com.SoyHenry.FinancialHub.mapper;

import com.SoyHenry.FinancialHub.dto.account.AccountDtoRequest;
import com.SoyHenry.FinancialHub.dto.account.AccountDtoResponse;
import com.SoyHenry.FinancialHub.entities.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper{

AccountDtoRequest mapToDtoRequest(Account account);
AccountDtoResponse mapToDtoResponse(Account account);

Account mapToAccount(AccountDtoRequest accountDtoRequest);

}
