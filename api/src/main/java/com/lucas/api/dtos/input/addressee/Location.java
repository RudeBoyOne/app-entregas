package com.lucas.api.dtos.input.addressee;

import com.lucas.api.dtos.input.addressee.serve.Coordinates;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {

    private Coordinates coordinates;
}
