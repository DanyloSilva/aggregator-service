package com.devdan.aggregator_service;

import com.devdan.aggregator_service.domain.Ticker;
import com.devdan.aggregator_service.domain.TradeAction;
import com.devdan.aggregator_service.dto.TradeRequest;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.mockserver.model.RegexBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import shaded_package.org.checkerframework.checker.regex.qual.Regex;

import java.util.Objects;

public class CustomerTradeTest extends AbstractIntegrationTest{
    private static final Logger log = LoggerFactory.getLogger(CustomerTradeTest.class);
@Test
 public void TradeSucess(){

     var responseBody = this.stockServiceResource("stock-price-200.json");
     mockServerClient
             .when(HttpRequest.request("/stock/GOOGLE"))
             .respond(
                     HttpResponse.response(responseBody)
                             .withStatusCode(200)
                             .withContentType(MediaType.APPLICATION_JSON)
             );

     var customerResponseBody = this.stockServiceResource("customer-trade-200.json");

     mockServerClient
             .when(HttpRequest.request("/customer/1/trade")
                     .withMethod("POST")
                     .withBody(RegexBody.regex(".*\"price\":110.*"))
             ).respond(
                     HttpResponse.response(customerResponseBody)
                             .withStatusCode(200)
                             .withContentType(MediaType.APPLICATION_JSON)
             );


     var traderequest = new TradeRequest(Ticker.GOOGLE, TradeAction.BUY,2);
     postTade(traderequest, HttpStatus.OK);
 }

    private WebTestClient.BodyContentSpec postTade(TradeRequest tradeRequest,HttpStatus expectedStatus){
        return this.client.post()
                .uri("/customers/1/trade")
                .bodyValue(tradeRequest)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus)
                .expectBody()
                .consumeWith(e -> log.info("{}", new String(Objects.requireNonNull(e.getResponseBody()))));

    }



}
