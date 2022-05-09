package com.finalproyect.model.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KeycloakUserDataDto {

    private String keycloakId;

    private String fstName;

    private String lstName;

    private String email;
}
