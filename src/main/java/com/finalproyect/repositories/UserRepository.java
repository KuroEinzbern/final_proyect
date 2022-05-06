package com.finalproyect.repositories;

import com.finalproyect.entities.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users, Long> {
    Users findByEmail(String email);
    Users findByKeycloakId(String keycloakId);
}
