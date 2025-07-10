package com.devdan.aggregator_service.exeptions;

public class InvalidTradeRequestExeption  extends RuntimeException{


    public  InvalidTradeRequestExeption(String message){
        super(message);

    }
}
