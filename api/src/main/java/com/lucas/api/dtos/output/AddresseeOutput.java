package com.lucas.api.dtos.output;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AddresseeOutput {

    private UUID id;
    private String name;
    private String city;
    private String state;
    private String cep;
    private Long houseNumber;
    private String neighborhood;
    private String street;
    private String longitude;
    private String latitude;
}
