package com.finalproyect.repositories;

import com.finalproyect.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
     Product findByName(String name);
     Product findByProductCode(String productCode);

}
