package com.finalproyect.services;

import com.finalproyect.entities.Checkout;
import com.finalproyect.entities.User;
import com.finalproyect.exceptions.UserNotFoundException;
import com.finalproyect.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User retrieveUserById(Long id){
        Optional<User> user=this.userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException("No existe usuario con dicho ID");
    }

    public void addNewCheckout(User user, Checkout checkout){
        if(checkout!=null){
            user.setCheckout(checkout);
            this.userRepository.save(user);
        }
    }
}
