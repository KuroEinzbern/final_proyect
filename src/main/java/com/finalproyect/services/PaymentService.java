package com.finalproyect.services;


import com.finalproyect.model.patterns.PaymentStrategiesEnum;
import com.finalproyect.model.patterns.PaypalPaymentServiceAdapter;
import com.finalproyect.model.patterns.PaymentStrategy;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {

    private static Map<PaymentStrategiesEnum, PaymentStrategy> strategies;

    static {
        strategies=new HashMap<>();
        strategies.put(PaymentStrategiesEnum.PAYPAL,new PaypalPaymentServiceAdapter());
    }

    public PaymentStrategy getStrategy(PaymentStrategiesEnum key){
        return strategies.get(key);
    }

}
