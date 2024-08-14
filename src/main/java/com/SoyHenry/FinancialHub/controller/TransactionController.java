package com.SoyHenry.FinancialHub.controller;

import com.SoyHenry.FinancialHub.entities.Transaction;
import com.SoyHenry.FinancialHub.service.TransactionServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionServiceImp transactionServiceImp;

    @GetMapping
    public List<Transaction> getAllTransactions(){
        return transactionServiceImp.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Transaction>> getTransactionById(@PathVariable Long id){
        Optional<Transaction> existingTransaction = transactionServiceImp.getById(id);
        if(existingTransaction.isPresent()){
            return new ResponseEntity<>(existingTransaction, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction){
        Transaction createdTransaction = transactionServiceImp.create(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id){
        Optional<Transaction> existingTransaction = transactionServiceImp.getById(id);
        if(existingTransaction.isPresent()){
            transactionServiceImp.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
