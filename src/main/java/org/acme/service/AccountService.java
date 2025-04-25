package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.AddAccountReply;
import org.acme.AddAccountRequest;
import org.acme.GetAccountReply;
import org.acme.GetAccountRequest;
import org.acme.model.Account;

import java.util.HashMap;
import java.util.UUID;

// ApplicationScoped bedeutet, dass die Klasse nur einmal instanziiert wird
@ApplicationScoped
public class AccountService {
    HashMap<String, Account> accounts = new HashMap<>();

    public AddAccountReply addAccountFromRequest(AddAccountRequest request) {
        var id = UUID.randomUUID().toString();

        var newIban = "CH"+ UUID.randomUUID()
                .toString().replace("-", "")
                .substring(0, 19);

        Account newAccount =
                new Account(id, newIban, request.getName()
                        , request.getBalance());

        accounts.put(id, newAccount);

        AddAccountReply reply = AddAccountReply.newBuilder()
                .setId(id)
                .build();

        return reply;
    }

    public GetAccountReply getAccountByRequest(GetAccountRequest request) {
        var account =accounts.get(request.getId());

        var response = GetAccountReply.newBuilder()
                .setIban(account.getIban())
                .setName(account.getName())
                .setBalance(account.getBalance())
                .build();

        return response;
    }


}
