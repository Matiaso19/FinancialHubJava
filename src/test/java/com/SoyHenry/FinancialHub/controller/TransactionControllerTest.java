package com.SoyHenry.FinancialHub.controller;

import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoRequest;
import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoResponse;
import com.SoyHenry.FinancialHub.dto.transfer.TransferRequestDto;
import com.SoyHenry.FinancialHub.entities.Account;
import com.SoyHenry.FinancialHub.entities.Transaction;
import com.SoyHenry.FinancialHub.mapper.TransactionMapper;
import com.SoyHenry.FinancialHub.repository.AccountRepository;
import com.SoyHenry.FinancialHub.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.time.LocalDate;
import java.util.List;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private TransactionMapper transactionMapper;

    @MockBean
    private AccountRepository accountRepository;


    @Test
    @DisplayName("Test Integracion Get All Transactions")
    void getAllTransactionsIntegrationTest() throws Exception {

        //given
        Account account= new Account(1L, "Matias", 100.00, LocalDate.now(),null );
        TransactionDtoResponse transaction1 = new TransactionDtoResponse("DEBIT", 50.00, LocalDate.now(), account);
        TransactionDtoResponse transaction2 = new TransactionDtoResponse("CREDIT", 150.00, LocalDate.now(), account);

        List<TransactionDtoResponse> transactions = List.of(transaction1, transaction2);
        given(transactionService.getAll()).willReturn(transactions);

        //when-then
        mockMvc.perform(get("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].type", is("DEBIT")))
                .andExpect(jsonPath("$[1].type", is("CREDIT")));



    }

    @Test
    void getTransactionByIdIntegrationTest() throws Exception {

        //given
        Long id = 1L;
        Account account = new Account(1L, "Matias", 100.00, LocalDate.now(), null);
        TransactionDtoResponse transactionDtoResponse = new TransactionDtoResponse("DEBIT", 50.00, LocalDate.now(), account);

        given(transactionService.getById(id)).willReturn(transactionDtoResponse);

        //when-then
        mockMvc.perform(get("/api/v1/transactions/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("DEBIT"))
                .andExpect(jsonPath("$.amount").value(50.00))
                .andExpect(jsonPath("$.account.accountHolderName").value("Matias"));



    }

    @Test
    @DisplayName("Test Integracion Create Transaction")
    void createTransactionIntegrationTest() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        //given
        TransactionDtoRequest transactionDtoRequest = new TransactionDtoRequest();
        transactionDtoRequest.setAccountId(1L);
        transactionDtoRequest.setDate(LocalDate.now());
        transactionDtoRequest.setType("CREDIT");
        transactionDtoRequest.setAmount(150.00);

        willDoNothing().given(transactionService).create(any(TransactionDtoRequest.class));

        //when - then
        mockMvc.perform(post("/api/v1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionDtoRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Transaccion creada exitosamente"));


    }

    @Test
    @DisplayName("Test Integracion Transfer Funds")
    void transferFunds() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        //given
        TransactionDtoRequest sourceTransactionDto = new TransactionDtoRequest();
        sourceTransactionDto.setAmount(100.00);
        sourceTransactionDto.setType("DEBIT");
        sourceTransactionDto.setDate(LocalDate.now());
        sourceTransactionDto.setAccountId(1L);

        TransferRequestDto transferRequestDto = new TransferRequestDto();
        transferRequestDto.setSourceTransactionDto(sourceTransactionDto);
        transferRequestDto.setTargetAccountId(2L);

        willDoNothing().given(transactionService).transferFunds(any(TransactionDtoRequest.class), any(Long.class));

        //when-then
        mockMvc.perform(post("/api/v1/transactions/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transferRequestDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Transferencia realizada exitosamente"));

    }
}