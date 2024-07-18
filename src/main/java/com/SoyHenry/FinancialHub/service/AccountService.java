package com.SoyHenry.FinancialHub.service;

import com.SoyHenry.FinancialHub.model.Account;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public interface AccountService {

    List<Account> getAll();
    Optional<Account> getById(Long id);
    Account create(Account account);
    void delete(Long id);

}
