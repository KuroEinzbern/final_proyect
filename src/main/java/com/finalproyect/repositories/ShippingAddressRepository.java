package com.finalproyect.repositories;

import com.finalproyect.entities.ShippingAddress;
import org.springframework.data.repository.CrudRepository;

public interface ShippingAddressRepository extends CrudRepository<ShippingAddress,Long> {
}
