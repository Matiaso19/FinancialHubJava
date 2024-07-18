package com.SoyHenry.FinancialHub.service;

import com.SoyHenry.FinancialHub.model.Account;
import com.SoyHenry.FinancialHub.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    List<Account> accounts = new ArrayList<>();


    @Override
    public List<Account> getAll() {
        return accounts;
    }

    @Override
    public Optional<Account> getById(Long id) {
        return accounts.stream()
                .filter(account -> account.getId().equals(id))
                .findFirst();
    }

    @Override
    public Account create(Account account) {
        accounts.add(account);
        return account;
    }

    @Override
    public void delete(Long id) {
        accounts.removeIf(transaction -> transaction.getId().equals(id));
    }
}
