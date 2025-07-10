package com.devdan.aggregator_service.controller;

import com.devdan.aggregator_service.dto.CustomerInformation;
import com.devdan.aggregator_service.dto.StockTraadeResponse;
import com.devdan.aggregator_service.dto.TradeRequest;
import com.devdan.aggregator_service.service.CustomerPortfolioService;
import com.devdan.aggregator_service.validator.RequestValidator;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerPortfolioController {

    public final CustomerPortfolioService customerPortfolioService;
    public CustomerPortfolioController(CustomerPortfolioService customerPortfolioService) {
        this.customerPortfolioService = customerPortfolioService;
    }
    @GetMapping("/{customerId}")
    public Mono<CustomerInformation> getCustomerInformation(@PathVariable Integer customerId){
        return customerPortfolioService.getCustomerInformation(customerId);
    }

    @PostMapping("/{customerId}/trade")
    public Mono<StockTraadeResponse> trade(
            @PathVariable Integer customerId,
            @RequestBody TradeRequest tradeRequest
    ) {
        return Mono.just(tradeRequest)
                .transform(RequestValidator.validate())
                .flatMap(req -> this.customerPortfolioService.trade(customerId, req));
    }

}
