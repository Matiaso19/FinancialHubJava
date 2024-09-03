package com.SoyHenry.FinancialHub.service.impl;

import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoRequest;
import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoResponse;
import com.SoyHenry.FinancialHub.dto.transaction.TransactionFindByFilterDto;
import com.SoyHenry.FinancialHub.entities.Account;
import com.SoyHenry.FinancialHub.entities.Transaction;
import com.SoyHenry.FinancialHub.mapper.TransactionMapper;
import com.SoyHenry.FinancialHub.repository.AccountRepository;
import com.SoyHenry.FinancialHub.repository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionServiceImpl transactionService;


    @Test
    @DisplayName("Test caso de exito para obtener todas las transacciones")
    void getAllTransactionsTest() {

        //given
        List<Transaction> transactions = List.of(new Transaction(), new Transaction());
        when(transactionRepository.findAll()).thenReturn(transactions);
        when(transactionMapper.mapToDtoResponse(any(Transaction.class))).thenReturn(new TransactionDtoResponse());

        //when
        List<TransactionDtoResponse> result = transactionService.getAll();

        //then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionRepository, times(1)).findAll();
        verify(transactionMapper, times(2)).mapToDtoResponse(any(Transaction.class));
    }

    @Test
    @DisplayName("Caso de exito para obtener una transaccion by Id")
    void getByIdTransactionTest() {

        //given
        Long idTransaction = 1L;
        Transaction transaction = new Transaction();

        when(transactionRepository.findById(idTransaction)).thenReturn(Optional.of(transaction));
        when(transactionMapper.mapToDtoResponse(transaction)).thenReturn(new TransactionDtoResponse());

        //when
        TransactionDtoResponse result = transactionService.getById(idTransaction);

        //then
        verify(transactionRepository).findById(idTransaction);
        verify(transactionMapper).mapToDtoResponse(transaction);
        assertNotNull(result);


    }

    @Test
    @DisplayName("Caso de exito para creacion de transaccion")
    void createTransactionTest() {
        //given
        TransactionDtoRequest transactionDto = new TransactionDtoRequest();
        transactionDto.setAccountId(1L);
        transactionDto.setDate(LocalDate.now());
        transactionDto.setType("CREDIT");
        transactionDto.setAmount(100.00);

        Account account = new Account();
        account.setBalance(200.00);

        when(accountRepository.findById(transactionDto.getAccountId())).thenReturn(Optional.of(account));

        //when
        transactionService.create(transactionDto);

        //then
        assertEquals(300.00, account.getBalance());
        verify(transactionRepository).save(any(Transaction.class));
        verify(accountRepository).save(account);

    }

    @Test
    @DisplayName("Test caso de exito para transferencia entre cuentas")
    void transferFundsTest() {

        //given
        TransactionDtoRequest sourceDto = new TransactionDtoRequest();
        sourceDto.setAmount(100.00);
        sourceDto.setType("DEBIT");
        sourceDto.setDate(LocalDate.now());
        sourceDto.setAccountId(1L);

        Long targetAccountId = 2L;

        Account sourceAccount = new Account();
        sourceAccount.setBalance(200.00);

        Account targetAccount = new Account();
        targetAccount.setBalance(100.00);

        when(accountRepository.findById(sourceDto.getAccountId())).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findById(targetAccountId)).thenReturn(Optional.of(targetAccount));

        //when
        transactionService.transferFunds(sourceDto, targetAccountId);

        //then
        assertEquals(100.00, sourceAccount.getBalance());
        assertEquals(200.00, targetAccount.getBalance());
        verify(transactionRepository, times(2)).save(any(Transaction.class));
        verify(accountRepository, times(2)).save(any(Account.class));



    }

    @Test
    @DisplayName("Test caso de exito para encontrar transacciones segun filtros aplicados")
    void findByFiltersTest(){
        //given
        Long accountId = 1L;
        String type = "CREDIT";
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);

        TransactionFindByFilterDto filterDto = new TransactionFindByFilterDto();
        filterDto.setAccountId(accountId);
        filterDto.setType(type);
        filterDto.setStartDate(startDate);
        filterDto.setEndDate(endDate);

        List<Transaction> transactions = List.of(new Transaction(), new Transaction());
        when(transactionRepository.findByFilters(accountId, type, startDate, endDate))
                .thenReturn(transactions);
        when(transactionMapper.mapToDtoResponse(any(Transaction.class)))
                .thenReturn(new TransactionDtoResponse());

        //when
        List<TransactionDtoResponse> result = transactionService.findByFilters(filterDto);

        //then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionRepository, times(1)).findByFilters(accountId, type, startDate, endDate);
        verify(transactionMapper, times(2)).mapToDtoResponse(any(Transaction.class));

    }
}