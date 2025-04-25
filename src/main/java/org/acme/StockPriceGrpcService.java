package org.acme;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import org.acme.service.StockPriceService;

import java.time.Duration;
import java.util.List;

@GrpcService
public class StockPriceGrpcService implements StockGrpc{

    @Inject
    StockPriceService service;

    @Override
    public Multi<StockReply> streamStockPrices(StockRequest request) {
        return Multi.createFrom().ticks().every(Duration.ofSeconds(1)).onItem()
                .transformToMulti(tick -> service.generatePrices(request.getSymbolList()))
                .concatenate();
    }
}
