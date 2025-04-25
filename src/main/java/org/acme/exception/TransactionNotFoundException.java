package org.acme.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String id) {
        super("Transaction with ID " + id + " not found.");
    }
}
