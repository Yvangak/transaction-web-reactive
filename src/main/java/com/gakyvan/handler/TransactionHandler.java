package com.gakyvan.handler;

import com.gakyvan.domain.Transaction;
import com.gakyvan.service.implementation.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class TransactionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionHandler.class);
    private TransactionService transactionService;

    @Autowired
    public TransactionHandler(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public Mono<ServerResponse> saveTransaction(ServerRequest request) {
        Instant startTime = Instant.now();
        LOGGER.info("[Started] TransactionHandler-saveTransaction ");

        AtomicReference<String> code = new AtomicReference<>();
        Flux<Transaction> transactions = request.bodyToFlux(Transaction.class)
                .flatMap(transaction -> transactionService.saveTransaction(transaction))
                .doOnNext(transaction -> code.set(transaction.get_id()));

        return Mono.from(transactions)
                .flatMap(transaction -> ServerResponse
                        .created(URI.create("/api/webreactive/v1/transactions/" + code))
                        .contentType(APPLICATION_JSON).body(fromValue(transaction))
                ).doOnError(error -> {
                    long completedIn = Duration.between(startTime, Instant.now()).toMillis();
                    LOGGER.error("[Completed-With-Error] TransactionHandler-saveTransaction ", completedIn,
                            error.getMessage(), error);
                }).doOnSuccess(success -> {
                    long completedIn = Duration.between(startTime, Instant.now()).toMillis();
                    LOGGER.info("[Completed-With-Success] TransactionHandler-saveTransaction {} ms", completedIn);
                });
    }

    public Mono<ServerResponse> getTransaction(ServerRequest request) {
        Instant startTime = Instant.now();
        LOGGER.info("[Started] TransactionHandler-getTransaction ");

        String transactionCode = request.pathVariable("code");
        return transactionService.getTransactionByCode(transactionCode)
                .flatMap(transaction -> ServerResponse.ok().contentType(APPLICATION_JSON).body(fromValue(transaction)))
                .switchIfEmpty(ServerResponse.notFound().build())
                .doOnError(error -> {
                    long durationInMs = Duration.between(startTime, Instant.now()).toMillis();
                    LOGGER.error("[Completed] TransactionHandler-getTransaction {}ms, {}, {}", durationInMs, error.getMessage(), error);
                })
                .doOnSuccess(success -> {
                    long durationInMs = Duration.between(startTime, Instant.now()).toMillis();
                    LOGGER.info("[Completed] TransactionHandler-getTransaction in {}ms with {} status code", durationInMs,
                            success.rawStatusCode());
                });
    }
}
