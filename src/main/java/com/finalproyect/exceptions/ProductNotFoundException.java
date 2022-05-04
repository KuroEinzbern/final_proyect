package com.finalproyect.exceptions;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductNotFoundException extends RuntimeException{
    String message;
}
