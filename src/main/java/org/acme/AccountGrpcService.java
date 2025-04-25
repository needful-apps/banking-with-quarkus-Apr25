package org.acme;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.acme.service.AccountService;

@GrpcService
public class AccountGrpcService implements AccountGrpc{

    // Inject bedeutet, dass die Klasse von Quarkus instanziiert wird
    @Inject
    AccountService accountService;

    @Override
    public Uni<AddAccountReply> addAccount(AddAccountRequest request) {
        var response = accountService.addAccountFromRequest(request);

        return Uni.createFrom().item(response);
    }

    @Override
    public Uni<GetAccountReply> getAccount(GetAccountRequest request) {
        var reply = accountService.getAccountByRequest(request);

        return Uni.createFrom().item(reply);
    }
}
