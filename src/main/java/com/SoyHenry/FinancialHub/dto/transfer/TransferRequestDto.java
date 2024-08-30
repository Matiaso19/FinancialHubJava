package com.SoyHenry.FinancialHub.dto.transfer;

import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TransferRequestDto {
    @NotNull(message = "La transaccion de origen no puede ser nula")
    @Valid
    private TransactionDtoRequest sourceTransactionDto;

    @NotNull(message = "El ID de la cuenta de destino no puede ser nulo")
    private Long targetAccountId;
}
