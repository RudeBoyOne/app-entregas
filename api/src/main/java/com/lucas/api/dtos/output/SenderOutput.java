package com.lucas.api.dtos.output;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SenderOutput {

    private UUID id;
    private String name;
    private Long contract;
    private String cpf;
}
