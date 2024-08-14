package com.SoyHenry.FinancialHub.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class AccountDtoRequest {

    private String accountHolderName;
    private Double balance;
    private LocalDate openingDate;

}
