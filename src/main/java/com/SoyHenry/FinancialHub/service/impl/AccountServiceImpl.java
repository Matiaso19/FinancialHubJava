package com.SoyHenry.FinancialHub.service.impl;

import com.SoyHenry.FinancialHub.dto.account.AccountDtoRequest;
import com.SoyHenry.FinancialHub.dto.account.AccountDtoResponse;
import com.SoyHenry.FinancialHub.dto.user.UserEntityDtoResponse;
import com.SoyHenry.FinancialHub.entities.Account;
import com.SoyHenry.FinancialHub.entities.UserEntity;
import com.SoyHenry.FinancialHub.mapper.AccountMapper;
import com.SoyHenry.FinancialHub.repository.AccountRepository;
import com.SoyHenry.FinancialHub.repository.UserRepository;
import com.SoyHenry.FinancialHub.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserRepository userRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.userRepository = userRepository;
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
        //search user by id
        UserEntity user = userRepository.findById(accountDtoRequest.getUserId())
                .orElseThrow(()-> new RuntimeException("Usuario con Id: " + accountDtoRequest.getUserId() + " no encontrado"));

        System.out.println(user);

        Account account = accountMapper.mapToAccount(accountDtoRequest);

        account.setUser(user);
        user.setAccount(account);

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
