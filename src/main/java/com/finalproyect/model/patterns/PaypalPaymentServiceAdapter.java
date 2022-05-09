package com.finalproyect.model.patterns;

import java.util.UUID;

public class PaypalPaymentServiceAdapter implements PaymentStrategy {
    @Override
    public UUID pay() {
        return new UUID(123, 100);
    }
}
