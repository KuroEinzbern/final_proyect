package com.finalproyect.model.dtos;

import com.finalproyect.entities.City;
import com.finalproyect.entities.Country;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CityDto {


    private String name;

    private Country country;

    public CityDto(City city){
        this.name=city.getName();
        this.country=city.getCountry();
    }
}
