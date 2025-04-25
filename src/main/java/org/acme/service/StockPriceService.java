package org.acme.service;

import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.StockReply;

import java.time.Duration;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class StockPriceService {

    private final Random random = new Random();

    public Multi<StockReply> generatePrices(List<String> symbols) {
        return Multi.createFrom().iterable(
                symbols.stream().map(
                        symbol -> StockReply.newBuilder()
                                .setSymbol(symbol)
                                .setPrice(50 + random.nextDouble() * 100)
                                .setTimestamp(System.currentTimeMillis())
                                .build()
                ).toList());
    }
}
