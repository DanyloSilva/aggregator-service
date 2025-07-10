package com.devdan.aggregator_service.client;

import com.devdan.aggregator_service.dto.CustomerInformation;
import com.devdan.aggregator_service.dto.StockTraadeResponse;
import com.devdan.aggregator_service.dto.StockTradeRequest;
import com.devdan.aggregator_service.dto.TradeRequest;
import com.devdan.aggregator_service.exeptions.ApplicationExeptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException.NotFound;
import org.springframework.web.reactive.function.client.WebClientResponseException.BadRequest;

import reactor.core.publisher.Mono;

import java.util.Objects;


public class CustomerServiceClient {

    private static final Logger log = LoggerFactory.getLogger(CustomerServiceClient.class);
    private final WebClient client;

    public CustomerServiceClient(@Qualifier("customerServiceClient") WebClient client) {
        this.client = client;
    }


    public Mono<CustomerInformation> getCustomerInformation(Integer customerId){
        return client.get()
                .uri("/customer/{customerId}", customerId)
                .retrieve()
                .bodyToMono(CustomerInformation.class)
                .onErrorResume(NotFound.class, ex -> ApplicationExeptions.customerNotFound(customerId));
    }

    public Mono<StockTraadeResponse> trade(Integer customerId, StockTradeRequest tradeRequest){
        return this.client.post()
                .uri("/customer/{customerId}/trade", customerId)
                .bodyValue(tradeRequest)
                .retrieve()
                .bodyToMono(StockTraadeResponse.class)
                .onErrorResume(NotFound.class, ex -> ApplicationExeptions.customerNotFound(customerId))
                .onErrorResume(BadRequest.class, this::handlerExeption);
    }

    private <T>Mono<T> handlerExeption(BadRequest exception){
        var pd = exception.getResponseBodyAs(ProblemDetail.class);
        var menssage = Objects.nonNull(pd) ? pd.getDetail() : exception.getMessage();
        log.error("Bad request: {}", pd);
        return ApplicationExeptions.invalidTradeRequest(menssage);
    }
}

