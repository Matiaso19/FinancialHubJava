package com.SoyHenry.FinancialHub.service;

import com.SoyHenry.FinancialHub.dto.AccountDtoRequest;
import com.SoyHenry.FinancialHub.dto.AccountDtoResponse;
import com.SoyHenry.FinancialHub.entities.Account;
import com.SoyHenry.FinancialHub.mapper.AccountMapper;
import com.SoyHenry.FinancialHub.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public List<AccountDtoResponse> getAll() {
        List<Account> accounts = accountRepository.findAll();
        return mapToDtoList(accounts);
    }

    @Override
    public AccountDtoResponse getById(Long id) {
        Optional<Account> account = accountRepository.findById(id);
        return account.map(accountMapper::mapToDtoResponse).orElse(null);

    }

    @Override
    public void create(AccountDtoRequest accountDtoRequest) {
        Account account = accountMapper.mapToAccount(accountDtoRequest);
        accountRepository.save(account);
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    public void update(Long id, AccountDtoRequest accountDtoRequest) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        System.out.println(accountDtoRequest);

        if(optionalAccount.isPresent()){
            Account account = optionalAccount.get();
            account.setAccountHolderName(accountDtoRequest.getAccountHolderName());
            account.setBalance(accountDtoRequest.getBalance());
            account.setOpeningDate(accountDtoRequest.getOpeningDate());
            accountRepository.save(account);
        }
    }

    private List<AccountDtoResponse> mapToDtoList(List<Account> accounts){
        return accounts.stream()
                .map(accountMapper::mapToDtoResponse)
                .collect(Collectors.toList());
    }
}
