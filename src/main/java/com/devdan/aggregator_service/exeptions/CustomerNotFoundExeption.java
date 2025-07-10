package com.devdan.aggregator_service.exeptions;

public class CustomerNotFoundExeption extends RuntimeException{
    private static final String MESSAGE = "Customer [id = %d] is not found";
    public CustomerNotFoundExeption(Integer id) {
        super(String.format(MESSAGE, id));
    }
}
