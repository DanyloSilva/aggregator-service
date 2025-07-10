package com.devdan.aggregator_service.config;


import com.devdan.aggregator_service.client.CustomerServiceClient;
import com.devdan.aggregator_service.client.StockServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private static Logger log = LoggerFactory.getLogger(WebClientConfig.class);

    @Bean
    public CustomerServiceClient customerServiceClient(@Value("${customer.service.url}") String baseUrl){
        return new CustomerServiceClient(createWebCliente(baseUrl));

    }

    @Bean
    public StockServiceClient stockServiceClient(@Value("${stock.service.url}") String baseUrl) {

     return new StockServiceClient(createWebCliente(baseUrl));
    }



             private WebClient createWebCliente(String baseUrl){

         log.info("base url: {}", baseUrl);
         return WebClient.builder()
                 .baseUrl(baseUrl)
                 .build();
        }
    }


