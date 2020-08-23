package com.gakyvan.controller;

import com.gakyvan.domain.Transaction;
import com.gakyvan.service.ITransactionService;
import com.gakyvan.service.implementation.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/api/webreactive/v1/transactions")
public class TransactionController {
    private ITransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public Mono<Transaction> saveTransaction(@RequestBody Transaction transaction){
        return transactionService.saveTransaction(transaction);
    }

    @GetMapping("/{code}")
    public Mono<Transaction> getTransaction(@PathVariable(value = "code") String code){
        return transactionService.getTransactionByCode(code);
    }

    @GetMapping("/{cardNumber}")
    public Flux<Transaction> getTransactions(@PathVariable(value = "cardNumber") String cardNumber){
        return transactionService.getTransactionByCardNumber(cardNumber);
    }
}
