package com.gakyvan.service.implementation;

import com.gakyvan.domain.Transaction;
import com.gakyvan.repository.ITransactionRepository;
import com.gakyvan.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionService implements ITransactionService {
    private ITransactionRepository iTransactionRepository;

    @Autowired
    public TransactionService(ITransactionRepository iTransactionRepository){
        this.iTransactionRepository = iTransactionRepository;
    }

    @Override
    public Mono<Transaction> saveTransaction(Transaction transaction) {
        return iTransactionRepository.save(transaction);
    }

    @Override
    public Mono<Transaction> getTransactionByCode(String code) {
        return iTransactionRepository.findByTransactionCode(code);
    }

    @Override
    public Flux<Transaction> getTransactionByCardNumber(String cardNumber) {
        return iTransactionRepository.findByCardNumber(cardNumber);
    }
}
