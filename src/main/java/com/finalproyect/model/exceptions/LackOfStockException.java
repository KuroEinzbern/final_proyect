package com.finalproyect.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LackOfStockException extends RuntimeException{
    private String errorMessage;
}
