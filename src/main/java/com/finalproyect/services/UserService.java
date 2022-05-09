package com.finalproyect.services;

import com.finalproyect.entities.Checkout;
import com.finalproyect.entities.Users;
import com.finalproyect.model.dtos.KeycloakUserDataDto;
import com.finalproyect.model.exceptions.UserNotFoundException;
import com.finalproyect.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    KeycloakContextService keycloakContextService;

    public Users retrieveUserById(Long id){
        Optional<Users> user=this.userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException("No existe usuario con dicho ID");
    }

    public void addNewCheckout(Users users, Checkout checkout){
        if(checkout!=null){
            users.setCheckout(checkout);
            this.userRepository.save(users);
        }
    }

    public Users retrieveUser(){
        KeycloakUserDataDto userData= keycloakContextService.contextData();
        Users optionalUser= this.userRepository.findByKeycloakId(userData.getKeycloakId());
        if(optionalUser!=null){
            return optionalUser;
        }
        else {
            Users newUser= new Users();
            newUser.setEmail(userData.getEmail());
            newUser.setName(userData.getFstName() + " " + userData.getLstName());
            newUser.setKeycloakId(userData.getKeycloakId());
            return this.userRepository.save(newUser);
        }
    }
}
