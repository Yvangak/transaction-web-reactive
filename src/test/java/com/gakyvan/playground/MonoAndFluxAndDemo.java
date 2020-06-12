package com.gakyvan.playground;

import reactor.core.publisher.Flux;

public class MonoAndFluxAndDemo {
    public static void main(String... args) {
        //fluxSetUpWithLogToShowStepsAndEndsInOnComplete();

       // fluxSetUpWithLogToShowStepsWithErrorCausingOnErrorEvent();

        fluxSetUpWithLogToShowStepsWithErrorBeforeAddingNewValueFailsBecauseOfOnErrorEvent();
    }

    public static void fluxSetUpWithLogToShowStepsAndEndsInOnComplete() {
        Flux<String> stringFlux = Flux.just("Jane", "Michelle", "Yan")
                .concatWith(Flux.just("Yvon"))
                .log();

        stringFlux
                .subscribe(name -> System.out.println(name));
    }

    public static void fluxSetUpWithLogToShowStepsWithErrorCausingOnErrorEvent() {
        Flux<String> stringFlux = Flux.just("Jane", "Michelle", "Yan")
                .concatWith(Flux.just("Yvon"))
                .concatWith(Flux.error(new RuntimeException("Exception Thrown")))
                .log();

        stringFlux
                .subscribe(System.out::println,
                        ex -> System.err.println("Error logged: " + ex));
    }

    public static void fluxSetUpWithLogToShowStepsWithErrorBeforeAddingNewValueFailsBecauseOfOnErrorEvent(){
        Flux<String> stringFlux = Flux.just("Jane", "Michelle", "Yan")
                .concatWith(Flux.error(new RuntimeException("Exception Thrown")))
                .concatWith(Flux.just("Yvon"))
                .log();

        stringFlux
                .subscribe(System.out::println,
                        ex -> System.err.println("Error logged: " + ex));
    }
}
