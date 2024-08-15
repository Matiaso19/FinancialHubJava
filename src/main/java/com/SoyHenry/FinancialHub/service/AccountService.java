package com.SoyHenry.FinancialHub.service;

import com.SoyHenry.FinancialHub.dto.account.AccountDtoRequest;
import com.SoyHenry.FinancialHub.dto.account.AccountDtoResponse;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface AccountService {

    List<AccountDtoResponse> getAll();
    AccountDtoResponse getById(Long id);
    void create(AccountDtoRequest accountDtoRequest);
    void delete(Long id);
    void update(Long id, AccountDtoRequest accountDtoRequest);

}
