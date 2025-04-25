package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.*;
import org.acme.exception.TransactionNotFoundException;
import org.acme.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TransactionService {

    @Inject
    AccountService accountService;

    HashMap<String, Transaction> transactions = new HashMap<>();

    public AddTransactionReply addTransactionFromRequest(AddTransactionRequest request) {
        var id = UUID.randomUUID().toString();
        var transaction = new Transaction(id, request.getSender(), request.getReceiver(), request.getAmount());

        // Update the account balances
        accountService.changeAccountBalance(request.getSender(), -request.getAmount());
        accountService.changeAccountBalance(request.getReceiver(), request.getAmount());

        transactions.put(id, transaction);
        var reply = AddTransactionReply.newBuilder()
                .setId(id)
                .build();

        return reply;
    }

    public GetTransactionReply getTransactionFromRequest(GetTransactionRequest request) {
        var transaction = transactions.get(request.getId());

        if (transaction == null) {
            throw new TransactionNotFoundException(request.getId());
        }

        var reply = GetTransactionReply.newBuilder()
                .setSender(transaction.getSenderId())
                .setReceiver(transaction.getReceiverId())
                .setAmount(transaction.getAmount())
                .build();

        return reply;
    }

    public GetAllTransactionReply getAllTransactionFromRequest() {
        List<GetTransactionReply> transactionList = new ArrayList<>();

        for (var transaction : transactions.values()) {
            var transactionReply = GetTransactionReply.newBuilder()
                    .setSender(transaction.getSenderId())
                    .setReceiver(transaction.getReceiverId())
                    .setAmount(transaction.getAmount())
                    .build();
            transactionList.add(transactionReply);
        }

        var reply = GetAllTransactionReply.newBuilder()
                .addAllTransactions(transactionList)
                .build();

        return reply;
    }
}
