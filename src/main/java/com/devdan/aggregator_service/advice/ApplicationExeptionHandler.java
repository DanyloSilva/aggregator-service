package com.devdan.aggregator_service.advice;

import com.devdan.aggregator_service.exeptions.CustomerNotFoundExeption;
import com.devdan.aggregator_service.exeptions.InvalidTradeRequestExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.function.Consumer;

@ControllerAdvice
public class ApplicationExeptionHandler {

    @ExceptionHandler(CustomerNotFoundExeption.class)
    public ProblemDetail handlerExeption(CustomerNotFoundExeption ex){
       return build(HttpStatus.NOT_FOUND,ex, problemDetail -> {
         problemDetail.setType(URI.create("http://exemple.com/problems/NOT-FOUD"));
         problemDetail.setTitle("Custumer Not Found");
       }) ;


    }

    @ExceptionHandler(InvalidTradeRequestExeption.class)
    public ProblemDetail handlerExeption( InvalidTradeRequestExeption ex){
        return build(HttpStatus.BAD_REQUEST,ex, problemDetail -> {
            problemDetail.setType(URI.create("http://exemple.com/problems/invalid-trade-request"));
            problemDetail.setTitle("Invalid Trade Request");
        }) ;


    }




    private ProblemDetail build(HttpStatus status, Exception ex, Consumer<ProblemDetail> consumer) {
        var problem = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        consumer.accept(problem);
        return problem;
    }
}
