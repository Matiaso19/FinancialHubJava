package com.SoyHenry.FinancialHub.controller;

import com.SoyHenry.FinancialHub.dto.account.AccountDtoRequest;
import com.SoyHenry.FinancialHub.dto.account.AccountDtoResponse;
import com.SoyHenry.FinancialHub.entities.Account;
import com.SoyHenry.FinancialHub.mapper.AccountMapper;
import com.SoyHenry.FinancialHub.service.AccountService;
import com.SoyHenry.FinancialHub.service.impl.AccountServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private AccountMapper accountMapper;

    @Test
    @DisplayName("Test Integracion create")
    void createAccountIntegrationTest() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        //given
        Account account = new Account();
        account.setBalance(1500.00);
        account.setAccountHolderName("Matias Oli");
        account.setOpeningDate(LocalDate.now());

        AccountDtoRequest accountDtoRequestMock = accountMapper.mapToDtoRequest(account);

        willDoNothing().given(accountService).create(any(AccountDtoRequest.class));

        //when-then
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(account))

                ).andExpect(status().isCreated())
                .andExpect(content().string("Cuenta creada exitosamente"));

    }

    @Test
    @DisplayName("Test Integracion Get All Accounts")
    void getAllAccountsIntegrationTest() throws Exception {

        //given
        List<AccountDtoResponse> accounts = List.of(
                new AccountDtoResponse("Matias Oli", 1500.00, LocalDate.now(), null),
                new AccountDtoResponse("Maria Perez", 2000.00, LocalDate.now(), null)
        );

        given(accountService.getAll()).willReturn(accounts);

        //when-then
        mockMvc.perform(get("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].accountHolderName").value("Matias Oli"))
                .andExpect(jsonPath("$[1].accountHolderName").value("Maria Perez")
        );
    }

    @Test
    @DisplayName("Test Integracion Get Account By Id")
    void getAccountByIdIntegrationTest() throws Exception {

        //given
        Long id = 1L;
        AccountDtoResponse accountDtoResponse = new AccountDtoResponse("Matias Oli", 1500.00, LocalDate.now(), null);

        given(accountService.getById(id)).willReturn(accountDtoResponse);

        //when-then
        mockMvc.perform(get("/api/v1/accounts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountHolderName").value("Matias Oli"));

    }

    @Test
    @DisplayName("Test Integracion Delete Account By Id")
    void deleteAccountIntegrationTest() throws Exception {

        //given
        Long id = 1L;
        AccountDtoResponse accountDtoResponse = new AccountDtoResponse("Matias Oli", 1500.00, LocalDate.now(), null);

        given(accountService.getById(id)).willReturn(accountDtoResponse);
        willDoNothing().given(accountService).delete(id);

        //when - then
        mockMvc.perform(delete("/api/v1/accounts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Cuenta con id: 1 eliminado correctamente"));


    }

    @Test
    @DisplayName("Test Integracion Update Account By Id")
    void updateAccountIntegrationTest() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        //given
        Long id = 1L;
        AccountDtoResponse accountDtoResponse = new AccountDtoResponse("Matias Oli", 1500.00, LocalDate.now(), null);
        AccountDtoRequest accountDtoRequest = new AccountDtoRequest("Matias Oli", 2000.00, LocalDate.now());

        given(accountService.getById(id)).willReturn(accountDtoResponse);
        willDoNothing().given(accountService).update(id, accountDtoRequest);

        //when - then
        mockMvc.perform(put("/api/v1/accounts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(accountDtoRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Cuenta modificada correctamente"));

    }
}