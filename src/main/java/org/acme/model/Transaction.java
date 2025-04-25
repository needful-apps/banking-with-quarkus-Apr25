package org.acme.model;

public class Transaction {
    private String id;
    private String senderId;
    private String receiverId;
    private double amount;


    public Transaction(String id, String senderId, String receiverId, double amount) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public double getAmount() {
        return amount;
    }
}
