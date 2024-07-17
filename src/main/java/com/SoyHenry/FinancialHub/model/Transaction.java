package com.SoyHenry.FinancialHub.model;

import java.util.Date;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void transactionSummary(){
        System.out.println("Id: " + getId());
        System.out.println("Type: " + getType());
        System.out.println("Amount: " + getAmount());
        System.out.println("Date: " + getDate());
    }
}
