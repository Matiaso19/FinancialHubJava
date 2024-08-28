package com.SoyHenry.FinancialHub.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_holder_name")
    private String accountHolderName;
    private Double balance;

    @Column(name = "opening_date")
    private LocalDate openingDate;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Transaction> transactions;


    public void deposit(double amount){
        balance += amount;
    }
    public void withdraw(double amount){
        if(balance >= amount){
            balance -= amount;
        } else {
            System.out.println("No puedes realizar esta operacion porque el monto que intentas retirar es mayor que tu saldo disponible");
        }
    }
    public void printSummary(){
        System.out.println("Id: " + getId());
        System.out.println("Account Holder: " + getAccountHolderName());
        System.out.println("Opening Date: " + getOpeningDate());
        System.out.println("Balance: " + getBalance());
    }
}
