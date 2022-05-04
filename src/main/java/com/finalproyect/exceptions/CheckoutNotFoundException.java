package com.finalproyect.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CheckoutNotFoundException extends RuntimeException{
    private String message;
}
