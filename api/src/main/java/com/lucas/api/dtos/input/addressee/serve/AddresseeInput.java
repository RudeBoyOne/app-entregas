package com.lucas.api.dtos.input.addressee.serve;

import com.lucas.api.dtos.input.addressee.Location;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddresseeInput {

    private String name;
    private String city;
    private String state;
    private String cep;
    private Long houseNumber;
    private String neighborhood;
    private String street;
    private Location location;
}
