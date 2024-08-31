package com.SoyHenry.FinancialHub.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private Double amount;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;


    public void transactionSummary(){
        System.out.println("Id: " + getId());
        System.out.println("Type: " + getType());
        System.out.println("Amount: " + getAmount());
        System.out.println("Date: " + getDate());
    }
}
