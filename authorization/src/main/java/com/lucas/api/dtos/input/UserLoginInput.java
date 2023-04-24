package com.lucas.api.dtos.input;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserLoginInput {

    @NotBlank
    private String email;


    @NotBlank
    private String password;
}
