package com.SoyHenry.FinancialHub.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private Double amount;
    private LocalDate date;




    public void transactionSummary(){
        System.out.println("Id: " + getId());
        System.out.println("Type: " + getType());
        System.out.println("Amount: " + getAmount());
        System.out.println("Date: " + getDate());
    }
}
