package com.devdan.aggregator_service.dto;

import com.devdan.aggregator_service.domain.Ticker;

import java.time.LocalDateTime;

public record StockPriceResponse(Ticker ticker, Integer price) {
}
