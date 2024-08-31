package com.SoyHenry.FinancialHub.dto.transaction;

import com.SoyHenry.FinancialHub.entities.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDtoResponse {
    private String type;
    private Double amount;
    private LocalDate date;
    private Account account;
}
