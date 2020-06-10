package com.gakyvan.repository;

import com.gakyvan.domain.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ITransactionRepository extends ReactiveMongoRepository<Transaction, String> {

    Mono<Transaction> findByTransactionCode(String transactionCode);

    Flux<Transaction> findByCardNumber(String cardNumber);
}
