package com.lucas.api.dtos.output;

import com.lucas.domain.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserOutput {

    private UUID id;
    private String username;
    private String email;
    private List<Role> roles;
}
