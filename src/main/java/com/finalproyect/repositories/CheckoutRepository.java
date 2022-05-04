package com.finalproyect.repositories;

import com.finalproyect.entities.Checkout;
import org.springframework.data.repository.CrudRepository;

public interface CheckoutRepository extends CrudRepository<Checkout,Long> {
}
