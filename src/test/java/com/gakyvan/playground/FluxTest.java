package com.gakyvan.playground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    public void givenFluxWithElements_shouldVerifyTheFlowAndReceiveAOnCompleteEvent() {
        Flux<String> testStringFlux = Flux.just("Yves", "Yan", "Jonas")
                .log();

        StepVerifier.create(testStringFlux)
                .expectNext("Yves")
                .expectNext("Yan")
                .expectNext("Jonas")
                .verifyComplete();
    }

    @Test
    public void givenFluxWithElements_shouldVerifyTheFlowAndReceiveAOnErrorEvent() {
        Flux<String> testStringFlux = Flux.just("Yves", "Yan", "Jonas")
                .concatWith(Flux.error(new RuntimeException("Exception was thrown")))
                .log();

        StepVerifier.create(testStringFlux)
                .expectNext("Yves")
                .expectNext("Yan")
                .expectNext("Jonas")
               // .expectError(RuntimeException.class)
                .expectErrorMessage("Exception was thrown")
                .verify();
    }

    @Test
    public void givenFluxWithElements_shouldVerifyTheCountOfElementsAndCompleteEvent(){
        Flux<String> testStringFlux = Flux.just("Yves", "Yan", "Jonas")
                .log();

        StepVerifier.create(testStringFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void givenFluxWithElements_shouldVerifyTheCountOfElementsAndErrorEvent(){
        Flux<String> testStringFlux = Flux.just("Yves", "Yan", "Jonas")
                .concatWith(Flux.error(new RuntimeException("This is an Exception")))
                .log();

        StepVerifier.create(testStringFlux)
                .expectNextCount(3)
                .verifyError();
    }
}
