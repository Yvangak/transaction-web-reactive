package com.gakyvan.playground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {
    @Test
    public void givenMonoWithData_shouldVerifyValueAndExpectCompleteEvent(){
        Mono<String> testStringMono = Mono.just("Yves").log();
        StepVerifier.create(testStringMono)
                .expectNext("Yves")
                .verifyComplete();
    }

    @Test
    public void givenMonoWithError_shouldVerifyErrorEvent(){
        StepVerifier.create(Mono.error(new RuntimeException("Hello this is an exception"))
                .log())
                .expectError(RuntimeException.class)
                .verify();
    }
}
