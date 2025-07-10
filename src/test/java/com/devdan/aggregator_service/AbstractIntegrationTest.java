package com.devdan.aggregator_service;

import org.junit.jupiter.api.BeforeAll;
import org.mockserver.client.MockServerClient;
import org.mockserver.configuration.ConfigurationProperties;
import org.mockserver.springtest.MockServerTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@MockServerTest
@TestPropertySource(properties = {
        "customer.service.url=http://localhost:${mockServerPort}",
        "stock.service.url=http://localhost:${mockServerPort}"
})
public class AbstractIntegrationTest {

    protected MockServerClient mockServerClient;

    @Autowired
    protected WebTestClient client;

    @BeforeAll
    public static void setup() {
        ConfigurationProperties.disableLogging(true);
    }

    protected String resourceToString(String relativePath) {
        try (var inputStream = getClass().getClassLoader().getResourceAsStream(relativePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Recurso não encontrado: " + relativePath);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o recurso: " + relativePath, e);
        }
    }

    protected String customerServiceResource(String filename) {
        return resourceToString("customer-service/" + filename);
    }

    protected String stockServiceResource(String filename) {
        return resourceToString("stock-service/" + filename);
    }
}