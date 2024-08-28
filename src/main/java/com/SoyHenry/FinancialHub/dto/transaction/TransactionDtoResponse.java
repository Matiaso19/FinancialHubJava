package com.SoyHenry.FinancialHub.dto.transaction;

import com.SoyHenry.FinancialHub.entities.Account;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDtoResponse {
    private String type;
    private Double amount;
    private LocalDate date;
    private Account account;
}
