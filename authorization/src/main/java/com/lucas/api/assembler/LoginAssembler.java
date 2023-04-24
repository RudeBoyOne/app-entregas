package com.lucas.api.assembler;

import com.lucas.api.dtos.output.UserLoginOutput;
import com.lucas.domain.model.User;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoginAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    public UserLoginOutput toOutput(User user) {
        return modelMapper.map(user,  UserLoginOutput.class);
    }
}
