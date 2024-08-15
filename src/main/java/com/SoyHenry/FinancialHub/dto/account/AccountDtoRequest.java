package com.SoyHenry.FinancialHub.dto.account;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountDtoRequest {

    private String accountHolderName;
    private Double balance;
    private LocalDate openingDate;

}
