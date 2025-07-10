package com.devdan.aggregator_service.exeptions;

import reactor.core.publisher.Mono;

public class ApplicationExeptions extends RuntimeException {


public  static <T>Mono<T> customerNotFound(Integer customerId) {
    return Mono.error(new CustomerNotFoundExeption(customerId));
}

    public  static <T>Mono<T> invalidTradeRequest(String  message) {
        return Mono.error(new InvalidTradeRequestExeption(message));
    }

    public  static <T>Mono<T> missingTicker() {
        return Mono.error(new InvalidTradeRequestExeption("Tiker is required"));
    }
    public  static <T>Mono<T> missingTradeAction() {
        return Mono.error(new InvalidTradeRequestExeption("trade action is required"));
    }
    public  static <T>Mono<T> invalidQuality() {
        return Mono.error(new InvalidTradeRequestExeption("Quantity should be > 0"));
    }


}
