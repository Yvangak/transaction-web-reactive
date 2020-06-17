package com.gakyvan.controller;

import com.gakyvan.domain.Transaction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping(path = "/api/transactionManager")
public class DemoController {
    @GetMapping(path = "/demo", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Transaction> getTransactions() {
        Transaction firstTransaction = Transaction.builder()
                .id("TR00-00-00-1")
                .amount(20.00)
                .cardHolder("CARD-000-99989-00")
                .build();
        Transaction secondTransaction = Transaction.builder()
                .id("TR00-00-00-2")
                .amount(32.00)
                .cardHolder("CARD-111-99989-00")
                .build();

        return Flux.just(firstTransaction, secondTransaction)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(path = "/infiniteStream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Long> getInfiniteStream() {
        return Flux.interval(Duration.ofSeconds(1))
                .log();
    }
}
