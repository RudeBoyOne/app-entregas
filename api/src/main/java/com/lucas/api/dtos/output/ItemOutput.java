package com.lucas.api.dtos.output;

import com.lucas.domain.model.enums.TypeItem;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ItemOutput {

    private UUID id;
    private String code;
    private TypeItem typeItem;
    private SenderOutput owner;
}
