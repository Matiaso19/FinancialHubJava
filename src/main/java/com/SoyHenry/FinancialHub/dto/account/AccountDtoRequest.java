package com.SoyHenry.FinancialHub.dto.account;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountDtoRequest {

    @NotBlank(message = "El nombre del propietario de la cuenta no puede estar vacio")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "El nombre del propietario de la cuenta solo puede contener letras y espacios")
    @Size(min = 3, max = 100)
    private String accountHolderName;

    @NotNull(message = "El balance no puede ser nulo")
    @Min(value = 0, message = "El balance debe ser mayor o igual a 0")
    private Double balance;

    @NotNull(message = "La fecha de apertura no puede ser nula")
    @PastOrPresent(message = "La fecha de apertura debe ser pasada o presente")
    private LocalDate openingDate;

}
