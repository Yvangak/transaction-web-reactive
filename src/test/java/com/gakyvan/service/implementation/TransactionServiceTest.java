package com.gakyvan.service.implementation;

import com.gakyvan.domain.Transaction;
import com.gakyvan.repository.ITransactionRepository;
import com.gakyvan.service.ITransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {
    @Mock
    private ITransactionRepository iTransactionRepository;
    private ITransactionService subject;
    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        subject = new TransactionService(iTransactionRepository);
        transaction = Transaction.builder()
                .id(UUID.randomUUID().toString().toUpperCase())
                .transactionCode("TR00-00-00-1")
                .amount(20.00)
                .cardHolder("CARD-000-99989-00")
                .build();
    }

    @Test
    public void whenSaveTransaction_givenTransaction_shouldSaveAndReturnAMonoTransaction() {
        Mono<Transaction> expected = Mono.just(transaction);
        doReturn(expected).when(iTransactionRepository).save(transaction);
        Mono<Transaction> actual = subject.saveTransaction(transaction);
        assertEquals(expected, actual);
    }

    @Test
    public void whenGetTransactionByCode_givenTransactionCode_shouldReturnMonoTransaction() {
        String testTransactionCode = "TR00-00-00-1";
        Mono<Transaction> expected = Mono.just(transaction);
        doReturn(expected).when(iTransactionRepository).findByTransactionCode(testTransactionCode);
        Mono<Transaction> actual = subject.getTransactionByCode(testTransactionCode);
        StepVerifier.create(actual)
                .expectNext(transaction)
                .verifyComplete();
    }

    @Test
    public void whenGetTransactionByCardNumber_givenCardNumber_shouldReturnFluxTransactions() {
        String testCardNumber = "CARD-000-99989-00";
        Flux<Transaction> expected = Flux.just(transaction);
        doReturn(expected).when(iTransactionRepository).findByCardNumber(testCardNumber);
        Flux<Transaction> actual = subject.getTransactionByCardNumber(testCardNumber);
        StepVerifier.create(actual)
                .expectNext(transaction)
                .verifyComplete();
    }
}