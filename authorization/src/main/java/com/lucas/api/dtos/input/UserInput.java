package com.lucas.api.dtos.input;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UserInput {

    @NotBlank
    private String username;

    @Schema(required = true, description = "email válido: algumacoisa@outracoisa.com")
    @Email(regexp = "[A-Za-z0-9_.-]+@([A-Za-z0-9_]+.)+[A-Za-z]{2,4}")
    private String email;

    @NotBlank
    private String password;

    @NotNull
    public List<Long> roles;
}
