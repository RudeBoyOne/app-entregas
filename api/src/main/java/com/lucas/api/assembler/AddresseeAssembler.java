package com.lucas.api.assembler;

import com.lucas.api.dtos.input.addressee.serve.AddresseeInput;
import com.lucas.api.dtos.output.AddresseeOutput;
import com.lucas.domain.model.Addressee;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class AddresseeAssembler {
    ModelMapper modelMapper = new ModelMapper();

    public AddresseeOutput toOutput(Addressee addressee) {
        return modelMapper.map(addressee, AddresseeOutput.class);
    }

    public List<AddresseeOutput> toCollectionOutput(List<Addressee> addressees) {
        return addressees.stream().map(this::toOutput).collect(Collectors.toList());
    }

    public Addressee toEntity(AddresseeInput addresseeInput) {
        TypeMap<AddresseeInput, Addressee> addresseeInputAddresseeTypeMap = modelMapper.typeMap(AddresseeInput.class, Addressee.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getLocation().getCoordinates().getLatitude(),
                            Addressee::setLatitude);
                    mapper.map(src -> src.getLocation().getCoordinates().getLongitude(),
                            Addressee::setLongitude);
                });
        return addresseeInputAddresseeTypeMap.map(addresseeInput);
    }
}
