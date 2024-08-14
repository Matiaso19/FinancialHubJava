package com.SoyHenry.FinancialHub.entities;

import lombok.Data;

import java.util.Date;

@Data
public class Transaction {

    private Long id;
    private String type;
    private Double amount;
    private Date date;

    public Transaction() {
        this.date = new Date();
    }

    public Transaction(Long id, String type, Double amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.date = new Date();
    }


    public void transactionSummary(){
        System.out.println("Id: " + getId());
        System.out.println("Type: " + getType());
        System.out.println("Amount: " + getAmount());
        System.out.println("Date: " + getDate());
    }
}
