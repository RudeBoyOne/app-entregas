package com.lucas.api.dtos.input.addressee;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddresseeInputClient {

    @Schema(description = "nome completo do destinatário")
    @NotBlank
    private String name;

    @Schema(description = "cep válido sem caracteres especiais: 00000000")
    @NotBlank
    private String cep;

    @Schema(type = SchemaType.NUMBER, description = "número residencial válido sem caracteres especiais: 0000")
    @NotNull
    private Long houseNumber;

}
