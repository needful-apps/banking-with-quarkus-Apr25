package org.acme;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import org.acme.model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@GrpcService
public class AccountGrpcService implements AccountGrpc{
    List<Account> accounts = new ArrayList<>();

    @Override
    public Uni<AddAccountReply> addAccount(AddAccountRequest request) {
        var id = UUID.randomUUID().toString();

        var newIban = "CH"+ UUID.randomUUID()
                .toString().replace("-", "")
                .substring(0, 19);

        Account newAccount =
                new Account(id, newIban, request.getName()
                        , request.getBalance());

        accounts.add(newAccount);

        AddAccountReply reply = AddAccountReply.newBuilder()
                .setId(id)
                .build();

        return Uni.createFrom().item(reply);
    }

    @Override
    public Uni<GetAccountReply> getAccount(GetAccountRequest request) {
        var account = accounts.stream()
                .filter(a -> a.getId().equals(request.getId()))
                .findFirst()
                .orElse(null);

        if (account == null) {
            return Uni.createFrom().failure(new RuntimeException("Account not found"));
        }

        var reply = GetAccountReply.newBuilder()
                .setIban(account.getIban())
                .setName(account.getName())
                .setBalance(account.getBalance())
                .build();

        return Uni.createFrom().item(reply);
    }
}
