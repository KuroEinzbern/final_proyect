package com.finalproyect.services;

import com.finalproyect.model.dtos.KeycloakUserDataDto;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class KeycloakContextService {


    public KeycloakUserDataDto contextData(){
       KeycloakUserDataDto userDataDto = new KeycloakUserDataDto();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        KeycloakPrincipal principal = (KeycloakPrincipal)auth.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();
        String keycloakId= accessToken.getId();
        String email= accessToken.getEmail();
        String fstName=accessToken.getGivenName();
        String lstName=accessToken.getFamilyName();
        userDataDto.setKeycloakId(keycloakId);
        userDataDto.setEmail(email);
        userDataDto.setFstName(fstName);
        userDataDto.setLstName(lstName);
        return userDataDto;
    }
}
