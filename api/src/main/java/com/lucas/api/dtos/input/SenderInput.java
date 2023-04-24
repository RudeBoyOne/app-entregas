package com.lucas.api.dtos.input;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SenderInput {

    @Schema(description = "nome completo do remetente")
    @NotBlank
    private String name;

    @NotNull
    private Long contract;

    @Schema(description = "cpf válido com caracteres especiais: 000.000.000-00", required = true)
    @CPF
    private String cpf;
}
