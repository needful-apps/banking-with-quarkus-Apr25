package org.acme;

import io.quarkus.grpc.GrpcService;

import io.smallrye.mutiny.Uni;

@GrpcService
public class HelloGrpcService implements HelloGrpc {


    @Override
    public Uni<HelloReply> sayHello(HelloRequest request) {
        String returnItem = "Hello " + request.getName() + "!";
        return Uni
                .createFrom()
                .item(returnItem)
                .map(msg -> HelloReply.newBuilder()
                                    .setMessage(msg)
                                    .build());
    }
}
