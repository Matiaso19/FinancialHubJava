package com.SoyHenry.FinancialHub.controller;

import com.SoyHenry.FinancialHub.dto.AccountDtoRequest;
import com.SoyHenry.FinancialHub.dto.AccountDtoResponse;
import com.SoyHenry.FinancialHub.entities.Account;
import com.SoyHenry.FinancialHub.service.AccountServiceImpl;
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
    private AccountServiceImpl accountService;

    @GetMapping
    public ResponseEntity<List<AccountDtoResponse>> getAllAccounts(){
        try{
        List<AccountDtoResponse> accounts = accountService.getAll();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDtoResponse> getAccountById(@PathVariable Long id){
        try{
            AccountDtoResponse accountDtoResponse = accountService.getById(id);
            if(accountDtoResponse != null){
                return new ResponseEntity<>(accountDtoResponse, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> createAccount(@RequestBody AccountDtoRequest accountDtoRequest){
        try{
        accountService.create(accountDtoRequest);
        return new ResponseEntity<>("Cuenta creada exitosamente", HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>("HUbo un error al crear la cuenta: "+ e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        try {
            AccountDtoResponse eliminateAccount = accountService.getById(id);
            if(eliminateAccount != null) {
                accountService.delete(id);
                return new ResponseEntity<>("Cuenta con id: " + id + " eliminado correctamente", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("No se encontro la cuenta con id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la cuenta con id: " + id + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAccount(@PathVariable Long id, @RequestBody AccountDtoRequest accountDtoRequest){
        try {
            AccountDtoResponse updatedAccount = accountService.getById(id);
            if(updatedAccount != null) {
                accountService.update(id, accountDtoRequest);
                return new ResponseEntity<>("Cuenta modificada correctamente", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("No se encontro la cuenta con el id: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Hubo un error al modificar la cuenta " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
