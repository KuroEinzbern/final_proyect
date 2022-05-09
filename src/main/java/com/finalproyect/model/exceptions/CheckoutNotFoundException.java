package com.finalproyect.model.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CheckoutNotFoundException extends RuntimeException{
    private String errorMessage;
}
