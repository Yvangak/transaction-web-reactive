package com.gakyvan.service;

import com.gakyvan.domain.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionService {
    Mono<Transaction> saveTransaction(Transaction transaction);

    Mono<Transaction> getTransactionByCode(String code);

    Flux<Transaction> getTransactionByCardNumber(String cardNumber);
}
