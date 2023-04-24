package com.lucas.api.assembler;

import com.lucas.api.dtos.input.SenderInput;
import com.lucas.api.dtos.output.SenderOutput;
import com.lucas.domain.model.Sender;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class SenderAssembler {

    ModelMapper modelMapper = new ModelMapper();

    public SenderOutput toOutput(Sender sender) {
        return modelMapper.map(sender, SenderOutput.class);
    }

    public List<SenderOutput> toCollectionOutput(List<Sender> senders) {
        return senders.stream().map(this::toOutput).collect(Collectors.toList());
    }

    public Sender toEntity(SenderInput senderInput) {
        return modelMapper.map(senderInput, Sender.class);
    }
}
