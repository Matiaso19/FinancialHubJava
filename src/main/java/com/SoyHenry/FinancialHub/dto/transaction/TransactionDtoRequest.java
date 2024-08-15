package com.SoyHenry.FinancialHub.dto.transaction;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDtoRequest {
    private String type;
    private Double amount;
    private LocalDate date;
}
