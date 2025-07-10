package com.devdan.aggregator_service.validator;

import com.devdan.aggregator_service.dto.TradeRequest;
import com.devdan.aggregator_service.exeptions.ApplicationExeptions;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

public static UnaryOperator <Mono <TradeRequest>> validate(){

    return mono -> mono.filter(hasTicker())
            .switchIfEmpty(ApplicationExeptions.missingTicker())
            .filter(hasTradeAction())
                    .switchIfEmpty(ApplicationExeptions.missingTradeAction())
                    .filter(isvalidQuantity())
                    .switchIfEmpty(ApplicationExeptions.invalidQuality());
}
    private static Predicate<TradeRequest> hasTicker(){
        return  dto -> Objects.nonNull(dto.ticker());
    }

    private static Predicate<TradeRequest> hasTradeAction(){
        return  dto -> Objects.nonNull(dto.action());
    }
    private static Predicate<TradeRequest> isvalidQuantity(){
        return  dto -> Objects.nonNull(dto.quantity()) && dto.quantity() > 0;
    }


}
