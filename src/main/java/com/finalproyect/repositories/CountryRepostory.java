package com.finalproyect.repositories;

import com.finalproyect.entities.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepostory extends CrudRepository<Country,Long> {
}
