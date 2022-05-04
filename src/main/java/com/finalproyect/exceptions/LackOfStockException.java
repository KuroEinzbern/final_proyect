package com.finalproyect.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LackOfStockException extends RuntimeException{
    private String message;
}
