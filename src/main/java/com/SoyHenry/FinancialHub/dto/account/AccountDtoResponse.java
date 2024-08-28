package com.SoyHenry.FinancialHub.dto.account;

import com.SoyHenry.FinancialHub.entities.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDtoResponse {

    private String accountHolderName;
    private Double balance;
    private LocalDate openingDate;
    private List<Transaction> transactions;

}
