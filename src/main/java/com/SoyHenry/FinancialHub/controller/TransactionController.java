package com.SoyHenry.FinancialHub.controller;


import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoRequest;
import com.SoyHenry.FinancialHub.dto.transaction.TransactionDtoResponse;
import com.SoyHenry.FinancialHub.dto.transaction.TransactionFindByFilterDto;
import com.SoyHenry.FinancialHub.dto.transfer.TransferRequestDto;
import com.SoyHenry.FinancialHub.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transactions")
@Validated
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionDtoResponse>> getAllTransactions(){
        try{
            List<TransactionDtoResponse> transactions = transactionService.getAll();
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDtoResponse> getTransactionById(@PathVariable Long id){
        try{
            TransactionDtoResponse existingTransaction = transactionService.getById(id);
            if(existingTransaction != null){
                return new ResponseEntity<>(existingTransaction, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createTransaction(@RequestBody @Valid TransactionDtoRequest transactionDtoRequest){
        try{
            transactionService.create(transactionDtoRequest);
            return new ResponseEntity<>("Transaccion creada exitosamente", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("Hubo un error al crear la transaccion " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody @Valid TransferRequestDto transferRequestDto){
        try{
            transactionService.transferFunds(transferRequestDto.getSourceTransactionDto(), transferRequestDto.getTargetAccountId());
            return new ResponseEntity<>("Transferencia realizada exitosamente", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Hubo un error al realizar la transferencia: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<List<TransactionDtoResponse>> getTransactionByFilters(@Valid TransactionFindByFilterDto filterDto){
        try{
            List<TransactionDtoResponse> transactions = transactionService.findByFilters(filterDto);
            return new ResponseEntity<>(transactions, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteTransaction(@PathVariable Long id){
//        try {
//            TransactionDtoResponse existingTransaction = transactionService.getById(id);
//            if(existingTransaction != null){
//                transactionService.delete(id);
//                return new ResponseEntity<>("Transaccion eliminada correctamente", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("No se encontro la transaccion con el id: " + id, HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e){
//            return new ResponseEntity<>("Hubo un error al eliminar la transaccion con id: "+ id, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @PutMapping("/{id}")
//    public ResponseEntity<String> updateTransaction(@PathVariable Long id, @RequestBody TransactionDtoRequest transactionDtoRequest){
//        try{
//            TransactionDtoResponse updatedTransaction = transactionService.getById(id);
//            if(updatedTransaction != null){
//                transactionService.update(id, transactionDtoRequest);
//                return new ResponseEntity<>("Transaccion modificada correctamente", HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>("No se encontro la transaccion con id: " + id, HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e){
//            return new ResponseEntity<>("Hubo un error al modificar la transaccion", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}
