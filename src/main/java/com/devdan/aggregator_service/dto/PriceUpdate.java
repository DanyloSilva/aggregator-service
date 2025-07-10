package com.devdan.aggregator_service.dto;

import com.devdan.aggregator_service.domain.Ticker;

import java.time.LocalDateTime;

public record PriceUpdate(Ticker ticker, Integer price, LocalDateTime time) {
}
