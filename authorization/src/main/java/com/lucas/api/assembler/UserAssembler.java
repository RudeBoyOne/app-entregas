package com.lucas.api.assembler;

import com.lucas.api.dtos.input.UserInput;
import com.lucas.api.dtos.output.UserOutput;
import com.lucas.domain.model.User;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserAssembler {

    private ModelMapper modelMapper =  new ModelMapper();

    public UserOutput toOutput(User user) {
        return modelMapper.map(user,  UserOutput.class);
    }

    public User toEntity(UserInput userInput) {
        return modelMapper.map(userInput,  User.class);
    }
}
