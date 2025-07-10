package com.devdan.aggregator_service.dto;


import com.devdan.aggregator_service.domain.Ticker;
import com.devdan.aggregator_service.domain.TradeAction;

public record StockTraadeResponse(Integer customerId, Ticker ticker, Integer quantity, Integer price,
                                  TradeAction action, Integer totalPrice, Integer balance) {
}
