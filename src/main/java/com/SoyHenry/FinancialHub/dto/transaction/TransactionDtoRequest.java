package com.SoyHenry.FinancialHub.dto.transaction;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDtoRequest {

    @NotBlank(message = "El tipo de transaccion no puede estar vacio")
    @Pattern(regexp = "CREDIT|DEBIT", message = "El campo tipo solo admite los valores CREDIT o DEBIT")
    private String type;

    @Min(value = 0, message = "El monto debe ser mayor que 0")
    @NotNull(message = "El monto no puede ser nulo")
    private Double amount;

    @NotNull(message = "La fecha no puede ser nula")
    @PastOrPresent(message = "La fecha debe ser pasada o presente")
    private LocalDate date;

    @NotNull(message = "The accountId cannot be null.")
    private Long accountId;
}
