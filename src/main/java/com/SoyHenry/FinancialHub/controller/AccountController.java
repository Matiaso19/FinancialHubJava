package com.SoyHenry.FinancialHub.controller;

import com.SoyHenry.FinancialHub.model.Account;
import com.SoyHenry.FinancialHub.model.Transaction;
import com.SoyHenry.FinancialHub.service.AccountServiceImpl;
import com.SoyHenry.FinancialHub.service.TransactionServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    AccountServiceImpl accountService;

    @GetMapping
    public List<Account> getAllAccounts(){
        return accountService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Account>> getAccountById(@PathVariable Long id){
        Optional<Account> existingAccount = accountService.getById(id);
        if(existingAccount.isPresent()){
            return new ResponseEntity<>(existingAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        Account createdAccount = accountService.create(account);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long id){
        Optional<Account> existingAccount = accountService.getById(id);
        if(existingAccount.isPresent()){
            accountService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
