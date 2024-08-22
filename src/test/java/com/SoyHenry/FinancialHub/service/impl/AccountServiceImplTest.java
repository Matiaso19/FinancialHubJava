package com.SoyHenry.FinancialHub.service.impl;

import com.SoyHenry.FinancialHub.dto.account.AccountDtoRequest;
import com.SoyHenry.FinancialHub.dto.account.AccountDtoResponse;
import com.SoyHenry.FinancialHub.entities.Account;
import com.SoyHenry.FinancialHub.mapper.AccountMapper;
import com.SoyHenry.FinancialHub.repository.AccountRepository;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {


    @Mock
    private AccountRepository accountRepository;
    @Mock
    AccountMapper accountMapper;
    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    @DisplayName("Test caso de exito para creacion de Account")
    void createAccountTest(){

        //given
        AccountDtoRequest accountDtoRequestMock = new AccountDtoRequest();
        accountDtoRequestMock.setAccountHolderName("Matias");
        accountDtoRequestMock.setBalance(1500.00);
        accountDtoRequestMock.setOpeningDate(LocalDate.now());

        Account accountMock = new Account();
        accountMock.setAccountHolderName("Matias");
        accountMock.setBalance(1500.00);
        accountMock.setOpeningDate(LocalDate.now());

        when(accountMapper.mapToAccount(accountDtoRequestMock)).thenReturn(accountMock);

        //when
        accountService.create(accountDtoRequestMock);

        //then
        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);

        verify(accountRepository, times(1)).save(accountArgumentCaptor.capture());

        Account capturedAccount = accountArgumentCaptor.getValue();
        assertEquals("Matias", capturedAccount.getAccountHolderName());
        assertEquals(1500.00, capturedAccount.getBalance());
        assertEquals(LocalDate.now(), capturedAccount.getOpeningDate());


        verify(accountMapper, times(1)).mapToAccount(accountDtoRequestMock);

    }

    @Test
    @DisplayName("Test caso de exito para obtener todas las accounts")
    void getAllTest(){

        //given
        Account account1 = new Account();
        account1.setAccountHolderName("Matias");
        account1.setBalance(1500.00);
        account1.setOpeningDate(LocalDate.now());

        Account account2 = new Account();
        account2.setAccountHolderName("Ana");
        account2.setBalance(2000.00);
        account2.setOpeningDate(LocalDate.now().minusMonths(1));

        AccountDtoResponse dto1 = new AccountDtoResponse();
        dto1.setAccountHolderName("Matias");
        dto1.setBalance(1500.00);
        dto1.setOpeningDate(LocalDate.now());

        AccountDtoResponse dto2 = new AccountDtoResponse();
        dto2.setAccountHolderName("Ana");
        dto2.setBalance(2000.00);
        dto2.setOpeningDate(LocalDate.now().minusMonths(1));

        List<Account> accounts = Arrays.asList(account1, account2);
        List<AccountDtoResponse> dtos = Arrays.asList(dto1, dto2);

        when(accountRepository.findAll()).thenReturn(accounts);
        when(accountMapper.mapToDtoResponse(account1)).thenReturn(dto1);
        when(accountMapper.mapToDtoResponse(account2)).thenReturn(dto2);


        //when
        List<AccountDtoResponse> result = accountService.getAll();

        //then
        verify(accountRepository, times(1)).findAll();
        assertEquals(2, result.size(), "El tamaño de la lista de DTO deberia ser 2");


        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
    }
}


