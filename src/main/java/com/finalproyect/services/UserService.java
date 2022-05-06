package com.finalproyect.services;

import com.finalproyect.entities.Checkout;
import com.finalproyect.entities.Users;
import com.finalproyect.exceptions.UserNotFoundException;
import com.finalproyect.repositories.UserRepository;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal principal = (KeycloakPrincipal)auth.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();
        String keycloakId= accessToken.getId();
        Users optionalUser= this.userRepository.findByKeycloakId(keycloakId);
        if(optionalUser!=null){
            return optionalUser;
        }
        else {
            Users newUser= new Users();
            newUser.setEmail(accessToken.getEmail());
            newUser.setName(accessToken.getGivenName() + accessToken.getFamilyName());
            newUser.setKeycloakId(keycloakId);
            return this.userRepository.save(newUser);
        }
    }
}
