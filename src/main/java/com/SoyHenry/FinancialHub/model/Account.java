package com.SoyHenry.FinancialHub.model;

import java.util.Date;

public class Account {

    private Long id;
    private String accountHolderName;
    private Double balance;
    private Date openingDate;

    public Account() {
    }

    public Account(Long id, String accountHolderName, Double balance, Date openingDate) {
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.openingDate = openingDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

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
