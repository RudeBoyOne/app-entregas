package com.lucas.api.dtos.output;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserLoginOutput {

    private UUID id;
    private String username;
    private String email;
    private String token;
}
