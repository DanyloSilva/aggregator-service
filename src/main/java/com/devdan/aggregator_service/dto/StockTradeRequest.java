package com.devdan.aggregator_service.dto;


import com.devdan.aggregator_service.domain.Ticker;
import com.devdan.aggregator_service.domain.TradeAction;

public record StockTradeRequest(Ticker ticker, Integer price, Integer quantity, TradeAction action) {

    public  Integer getTotalPrice() {
        return price * quantity;
    }
}
