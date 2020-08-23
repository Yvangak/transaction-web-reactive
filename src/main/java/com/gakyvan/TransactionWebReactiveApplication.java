package com.gakyvan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
public class TransactionWebReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(TransactionWebReactiveApplication.class, args);
    }

}
