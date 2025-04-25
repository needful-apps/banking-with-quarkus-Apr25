package org.acme;

import io.grpc.Status;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import org.acme.exception.TransactionNotFoundException;
import org.acme.model.Transaction;
import org.acme.service.TransactionService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@GrpcService
public class TransactionGrpcService implements TransactionGrpc {
    private static final Logger LOG = Logger.getLogger(TransactionGrpcService.class.getName());

    @Inject
    private TransactionService transactionService;


    @Override
    public Uni<AddTransactionReply> addTransaction(AddTransactionRequest request) {
        LOG.info("Received request to add transaction: " + request);

        var reply = transactionService.addTransactionFromRequest(request);
        return Uni.createFrom().item(reply);
    }

    @Override
    public Uni<GetTransactionReply> getTransaction(GetTransactionRequest request) {
        try {
            var reply = transactionService.getTransactionFromRequest(request);
            return Uni.createFrom().item(reply);

        } catch (TransactionNotFoundException e) {
            LOG.warning("Transaction not found: " + e.getMessage());
            return Uni.createFrom().failure(Status.NOT_FOUND.withDescription(e.getMessage()).asRuntimeException());
        }
    }

    @Override
    public Uni<GetAllTransactionReply> getAllTransaction(GetAllTransactionRequest request) {
        var response =  transactionService.getAllTransactionFromRequest();

        return Uni.createFrom().item(response);
    }


}
