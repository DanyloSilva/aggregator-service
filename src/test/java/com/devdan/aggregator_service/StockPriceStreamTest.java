package com.devdan.aggregator_service;

import com.devdan.aggregator_service.dto.PriceUpdate;
import com.sun.nio.sctp.Association;
import org.junit.jupiter.api.Test;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.assertj.core.api.Assertions.assertThat;
import reactor.test.StepVerifier;

public class StockPriceStreamTest extends AbstractIntegrationTest{
    private static Logger log = LoggerFactory.getLogger(StockPriceStreamTest.class);

    @Test
    public void priceStream() {

        var responseBody = this.stockServiceResource("stock-price-stream-200.jsonl");
        mockServerClient
                .when(HttpRequest.request("/stock/price-stream"))
                .respond(
                        HttpResponse.response(responseBody)
                                .withStatusCode(200)
                                .withContentType(MediaType.parse("application/x-ndjson"))
                );

        this.client.get()
                .uri("/stock/price-stream")
                .accept(org.springframework.http.MediaType.TEXT_EVENT_STREAM)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .returnResult(PriceUpdate.class)
                .getResponseBody()
                .doOnNext(price -> log.info("{}", price))
                .as(StepVerifier::create)
                .assertNext(p -> assertThat(p.price()).isEqualTo(53))
                .assertNext(p -> assertThat(p.price()).isEqualTo(54))
                .assertNext(p -> assertThat(p.price()).isEqualTo(55))
                .expectComplete()
                .verify();
    }
}
