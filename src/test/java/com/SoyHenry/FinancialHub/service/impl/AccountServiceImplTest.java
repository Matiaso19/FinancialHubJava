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
import java.util.Optional;
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

    @Test
    @DisplayName("Caso de exito para eliminacion de account")
    void deleteAccountyByIdTest(){
        //given
        Long id = 1L;
        //when
        accountService.delete(id);
        //then
        verify(accountRepository, times(1)).deleteById(id);

        when(accountRepository.findById(id)).thenReturn(Optional.empty());
        Optional<Account> deletedAccount = accountRepository.findById(id);
        assertTrue(deletedAccount.isEmpty(), "La cuenta deberia estar eliminada");
    }

    @Test
    @DisplayName("Caso de exito para hacer update de un account")
    void updateAccountByIdTest(){
        //given
        Long id = 1L;

        Account accountMock = new Account();
        accountMock.setId(id);
        accountMock.setAccountHolderName("Matias");
        accountMock.setOpeningDate(LocalDate.of(2022,1,1));
        accountMock.setBalance(1500.00);

        AccountDtoRequest accountDtoRequestMock =new AccountDtoRequest();
        accountDtoRequestMock.setBalance(2000.00);
        accountDtoRequestMock.setAccountHolderName("Juan");
        accountDtoRequestMock.setOpeningDate(LocalDate.of(2023, 1,1));

        when(accountRepository.findById(id)).thenReturn(Optional.of(accountMock));

        //when
        accountService.update(id, accountDtoRequestMock);

        //then
        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository, times(1)).save(accountArgumentCaptor.capture());

        Account updatedAccount = accountArgumentCaptor.getValue();
        assertEquals("Juan", updatedAccount.getAccountHolderName());
        assertEquals(2000.00, updatedAccount.getBalance());
        assertEquals(LocalDate.of(2023, 1, 1), updatedAccount.getOpeningDate());

        verify(accountRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Caso de exito para encontrar una cuenta by id")
    void FindAccountByIdTestFound(){

        //given
        Long id = 1L;

        Account accountMock = new Account();
        accountMock.setBalance(1500.00);
        accountMock.setAccountHolderName("Matias");
        accountMock.setOpeningDate(LocalDate.of(2023,1,1));
        accountMock.setId(id);

        AccountDtoResponse accountDtoResponseMock = new AccountDtoResponse();
        accountDtoResponseMock.setAccountHolderName("Matias");
        accountDtoResponseMock.setOpeningDate(LocalDate.of(2023,1,1));
        accountDtoResponseMock.setBalance(1500.00);

        when(accountRepository.findById(id)).thenReturn(Optional.of(accountMock));
        when(accountMapper.mapToDtoResponse(accountMock)).thenReturn(accountDtoResponseMock);

        //when
        AccountDtoResponse result = accountService.getById(id);

        //then
        assertNotNull(result);

        verify(accountRepository, times(1)).findById(id);
        verify(accountMapper, times(1)).mapToDtoResponse(accountMock);

    }

    @Test
    @DisplayName("Caso de fallo al encontrar una cuenta by id")
    void FindAccountByIdTestFail(){

        //given
        Long id = 1L;

        when(accountRepository.findById(id)).thenReturn(Optional.empty());

        //when
        AccountDtoResponse result = accountService.getById(id);

        //then
        assertNull(result);

        verify(accountRepository, times(1)).findById(id);
        verify(accountMapper, times(0)).mapToDtoResponse(any(Account.class));

    }

}


