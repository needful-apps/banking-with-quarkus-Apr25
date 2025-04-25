package org.acme.model;

public class Account {
    private String id;
    private String iban;
    private  String name;
    private  double balance;

    public Account(String id, String iban, String name, double balance) {
        this.id = id;
        this.iban = iban;
        this.name = name;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getIban() {
        return iban;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void changeBalance(double amount) {
        this.balance += amount;
    }
}
