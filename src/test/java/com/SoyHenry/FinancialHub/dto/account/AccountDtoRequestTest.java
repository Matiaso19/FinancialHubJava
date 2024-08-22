package com.SoyHenry.FinancialHub.dto.account;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AccountDtoRequestTest {

    private Validator validator;

    @BeforeEach
    void setUp(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Test caso no exitoso: nombre del propietario vacio")
    void accountHolderNameBlankValidationTest(){
        //given
        AccountDtoRequest accountDtoRequest = new AccountDtoRequest();
        accountDtoRequest.setAccountHolderName("");
        accountDtoRequest.setBalance(1500.00);
        accountDtoRequest.setOpeningDate(LocalDate.now());

        //when
        Set<ConstraintViolation<AccountDtoRequest>> violations = validator.validate(accountDtoRequest);

        //then
        assertTrue(violations.size()>0);
        assertTrue(violations.stream().anyMatch(v->v.getMessage().equals("El nombre del propietario de la cuenta no puede estar vacio")));
    }

    @Test
    @DisplayName("Test caso no exitoso: nombre con caracteres no permitidos")
    void accountHolderNameInvalidPatternValidationTest(){
        //given
        AccountDtoRequest accountDtoRequest = new AccountDtoRequest();
        accountDtoRequest.setAccountHolderName("Matias123");
        accountDtoRequest.setBalance(1500.00);
        accountDtoRequest.setOpeningDate(LocalDate.now());

        //when
        Set<ConstraintViolation<AccountDtoRequest>> violations = validator.validate(accountDtoRequest);

        //then
        assertTrue(violations.size() > 0);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El nombre del propietario de la cuenta solo puede contener letras y espacios")));

    }

    @Test
    @DisplayName("Test caso no exitoso: balance nulo")
    void balanceNullValidationTest(){
        //given
        AccountDtoRequest accountDtoRequest = new AccountDtoRequest();
        accountDtoRequest.setBalance(null);
        accountDtoRequest.setAccountHolderName("Matias");
        accountDtoRequest.setOpeningDate(LocalDate.now());

        //when
        Set<ConstraintViolation<AccountDtoRequest>> violations = validator.validate(accountDtoRequest);

        //then
        assertTrue(violations.size() > 0);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El balance no puede ser nulo")));
    }

    @Test
    @DisplayName("Test caso no exitoso: balance negativo")
    void balanceNegativeValidationTest(){
        //given
        AccountDtoRequest accountDtoRequest = new AccountDtoRequest();
        accountDtoRequest.setOpeningDate(LocalDate.now());
        accountDtoRequest.setAccountHolderName("Matias");
        accountDtoRequest.setBalance(-1500.00);

        //when
        Set<ConstraintViolation<AccountDtoRequest>> violations = validator.validate(accountDtoRequest);

        //then
        assertTrue(violations.size() > 0);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("El balance debe ser mayor o igual a 0")));
    }

    @Test
    @DisplayName("Test caso no exitoso: fecha nula")
    void dateNullValidationTest(){
        //givwen
        AccountDtoRequest accountDtoRequest = new AccountDtoRequest();
        accountDtoRequest.setOpeningDate(null);
        accountDtoRequest.setAccountHolderName("Matias");
        accountDtoRequest.setBalance(1500.00);

        //when
        Set<ConstraintViolation<AccountDtoRequest>> violations = validator.validate(accountDtoRequest);

        //then
        assertTrue(violations.size() > 0);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("La fecha de apertura no puede ser nula")));
    }

    @Test
    @DisplayName("Test caso no exitoso: fecha futura")
    void dateFutureValidationTest() {
        //given
        AccountDtoRequest accountDtoRequest = new AccountDtoRequest();
        accountDtoRequest.setOpeningDate(LocalDate.now().plusDays(1));
        accountDtoRequest.setAccountHolderName("Matias");
        accountDtoRequest.setBalance(1500.00);

        //when
        Set<ConstraintViolation<AccountDtoRequest>> violations = validator.validate(accountDtoRequest);

        //then
        assertTrue(violations.size() > 0);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals("La fecha de apertura debe ser pasada o presente")));
    }
}