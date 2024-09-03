package com.SoyHenry.FinancialHub.dto.transaction;



import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
@Valid
public class TransactionFindByFilterDto {

    @Min(1)
    private Long accountId;

    @Pattern(regexp = "CREDIT|DEBIT", message = "El campo tipo solo admite los valores CREDIT o DEBIT")
    private String type;

    @PastOrPresent(message = "La fecha debe ser pasada o presente")
    private LocalDate startDate;

    @PastOrPresent(message = "La fecha debe ser pasada o presente")
    private LocalDate endDate;


}
