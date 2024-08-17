package com.SoyHenry.FinancialHub.dto.transaction;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDtoResponse {
    private String type;
    private Double amount;
    private LocalDate date;
    private Long accountId; // Referencia a la cuenta asociada
}
