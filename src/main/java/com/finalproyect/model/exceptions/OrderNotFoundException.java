package com.finalproyect.model.exceptions;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderNotFoundException extends RuntimeException{
    String errorMessage;
}
