package com.lucas.api.dtos.input;

import com.lucas.domain.model.enums.TypeItem;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
public class ItemInput {

    @Schema(description = "código do item válido é o número de contrato do seu referente remetente seguido por uma " +
            "letra: 00000/a")
    @NotBlank
    private String code;

    @NotNull
    private TypeItem typeItem;

    @NotNull
    private UUID owner;
}
