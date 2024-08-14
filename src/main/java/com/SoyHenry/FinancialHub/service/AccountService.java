package com.SoyHenry.FinancialHub.service;

import com.SoyHenry.FinancialHub.dto.AccountDtoRequest;
import com.SoyHenry.FinancialHub.dto.AccountDtoResponse;
import com.SoyHenry.FinancialHub.entities.Account;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public interface AccountService {

    List<AccountDtoResponse> getAll();
    AccountDtoResponse getById(Long id);
    void create(AccountDtoRequest accountDtoRequest);
    void delete(Long id);
    void update(Long id, AccountDtoRequest accountDtoRequest);

}
