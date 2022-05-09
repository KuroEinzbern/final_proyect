package com.finalproyect.controllers;

import com.finalproyect.model.exceptions.*;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
@NoArgsConstructor
@ResponseBody
public class ExceptionManager extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<Object> catchProductNotFound(ProductNotFoundException ex, WebRequest webRequest){
        return handleExceptionInternal(ex,ex.getErrorMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST,webRequest);
    }//crear dto para Ex.

    @ExceptionHandler(value = CheckoutNotFoundException.class)
    public ResponseEntity<Object> catchProductNotFound(CheckoutNotFoundException ex, WebRequest webRequest){
        return handleExceptionInternal(ex,ex.getErrorMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND,webRequest);
    }

    @ExceptionHandler(value = LackOfStockException.class)
    public ResponseEntity<Object> catchProductNotFound(LackOfStockException ex, WebRequest webRequest){
        return handleExceptionInternal(ex,ex.getErrorMessage(), new HttpHeaders(), HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE,webRequest);
    }


    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> catchUserNotFound(UserNotFoundException ex, WebRequest webRequest){
        return handleExceptionInternal(ex,ex.getErrorMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND,webRequest);
    }

    @ExceptionHandler(value = BadOrderException.class)
    public ResponseEntity<Object> catchBadOrder(BadOrderException ex, WebRequest webRequest){
        return handleExceptionInternal(ex,ex.getErrorMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND,webRequest);
    }
}