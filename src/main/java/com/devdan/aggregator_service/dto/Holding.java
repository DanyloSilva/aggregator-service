package com.devdan.aggregator_service.dto;


import com.devdan.aggregator_service.domain.Ticker;

public record Holding(Ticker ticker, Integer quantity) {
}
