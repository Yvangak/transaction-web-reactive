package com.gakyvan.config;

import com.gakyvan.handler.TransactionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;


@Configuration
@EnableWebFlux
public class TransactionRouterConfig {

    @Bean
    RouterFunction<ServerResponse> routes(TransactionHandler handler) {
        return RouterFunctions.route(GET("/api/webreactive/v1/transactions/{code}")
                .and(accept(APPLICATION_JSON)), handler::getTransaction)
                .andRoute(POST("/api/webreactive/v1/transactions")
                        .and(accept(APPLICATION_JSON)), handler::saveTransaction);
    }
}
