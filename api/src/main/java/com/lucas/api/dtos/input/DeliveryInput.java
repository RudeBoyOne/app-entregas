package com.lucas.api.dtos.input;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class DeliveryInput {

    @Schema(description = "valor de taxa válido com ponto flutuante: 00.00")
    @NotNull
    private BigDecimal tax;

    @NotNull
    private UUID sender;

    @NotNull
    private UUID addressee;

    @NotNull
    private UUID item;
}

