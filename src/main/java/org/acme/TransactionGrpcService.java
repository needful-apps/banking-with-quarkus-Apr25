package org.acme;

import io.grpc.Status;
import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import org.acme.exception.TransactionNotFoundException;
import org.acme.model.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@GrpcService
public class TransactionGrpcService implements TranscationGrpc{
    private static final Logger LOG = Logger.getLogger(TransactionGrpcService.class.getName());

    HashMap<String, Transaction> transactions = new HashMap<>();

    @Override
    public Uni<AddTranscationReply> addTranscation(AddTranscationRequest request) {
        LOG.info("Received request to add transaction: " + request);
        var id = UUID.randomUUID().toString();
        var transaction = new Transaction(id, request.getSender(), request.getReceiver(), request.getAmount());

        transactions.put(id, transaction);
        var reply = AddTranscationReply.newBuilder()
                .setId(id)
                .build();

        return Uni.createFrom().item(reply);
    }

    @Override
    public Uni<GetTranscationReply> getTranscation(GetTranscationRequest request) {
        var transaction = transactions.get(request.getId());

        if(transaction == null) {
            return Uni.createFrom()
                    .failure(Status.NOT_FOUND
                            .withDescription("Transaction not found")
                            .asRuntimeException());
        }

        var reply = GetTranscationReply.newBuilder()
                .setSender(transaction.getSenderId())
                .setReceiver(transaction.getReceiverId())
                .setAmount(transaction.getAmount())
                .build();

        return Uni.createFrom().item(reply);
    }
}
