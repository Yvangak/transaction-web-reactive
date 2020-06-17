package com.gakyvan.controller;

import com.gakyvan.domain.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebFluxTest
class DemoControllerTest {

    @Autowired
    private WebTestClient webTestClient;
    private Transaction firstTransaction;
    private Transaction secondTransaction;

    @BeforeEach
    public void setUp() {
        firstTransaction = Transaction.builder()
                .id("TR00-00-00-1")
                .amount(20.00)
                .cardHolder("CARD-000-99989-00")
                .build();
        secondTransaction = Transaction.builder()
                .id("TR00-00-00-2")
                .amount(32.00)
                .cardHolder("CARD-111-99989-00")
                .build();
    }

    @Test
    public void whenDemoEndPointCalled_shouldVerifyExactFlowOfData() {
        Flux<Transaction> actual = webTestClient.get()
                .uri("/api/transactionManager/demo")
                .exchange()
                .expectStatus()
                .isOk()
                .returnResult(Transaction.class)
                .getResponseBody();

        StepVerifier.create(actual)
                .expectSubscription()
                .expectNext(firstTransaction)
                .expectNext(secondTransaction)
                .verifyComplete();
    }

    @Test
    public void whenDemoEndPointCalled_shouldCheckTheNumberTransactionInTheFlow(){
        webTestClient.get()
                .uri("/api/transactionManager/demo")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Transaction.class)
                .hasSize(2);
    }

    @Test
    public void whenDemoEndPointCalled_shouldCheckTheReturnedList() {
        List<Transaction> expected = Arrays.asList(firstTransaction, secondTransaction);

        List<Transaction> actual = webTestClient.get()
                .uri("/api/transactionManager/demo")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Transaction.class)
                .returnResult().getResponseBody();

        assertEquals(expected, actual);
    }

    @Test
    public void whenDemoEndPointCalled_shouldCheckTheReturnedListUsingALAMDA() {
        List<Transaction> expected = Arrays.asList(firstTransaction, secondTransaction);

        webTestClient.get()
                .uri("/api/transactionManager/demo")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Transaction.class)
                .consumeWith(transactionResponse -> {
                   assertEquals(expected, transactionResponse.getResponseBody());
                });
    }

    @Test
    public void whenMonoDemoEndPointCalled_shouldReturnTransactionAndStatusOk() {
        Transaction expected = Transaction.builder()
                .id("TR00-00-00-1")
                .amount(20.00)
                .cardHolder("CARD-000-99989-00")
                .build();
        webTestClient.get()
                .uri("/api/transactionManager/mono")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Transaction.class)
                .consumeWith(actual ->{
                    assertEquals(expected, actual.getResponseBody());
                });
    }

    @Test
    public void whenInfiniteStreamEndPoint_shouldTestInfiniteStream() {
        Flux<Long> actual = webTestClient.get()
                .uri("/api/transactionManager/infiniteStream")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Long.class)
                .getResponseBody();

        StepVerifier.create(actual)
                .expectNext(0l)
                .expectNext(1L)
                .expectNext(2L)
                .thenCancel()
                .verify();
    }
}