package com.lucas.api.assembler;

import com.lucas.api.dtos.input.DeliveryInput;
import com.lucas.api.dtos.output.DeliveryOutput;
import com.lucas.domain.model.Delivery;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class DeliveryAssembler {

    ModelMapper modelMapper =  new ModelMapper();

    public DeliveryOutput toOutput(Delivery delivery) {
        return modelMapper.map(delivery,  DeliveryOutput.class);
    }

    public List<DeliveryOutput> toCollectionOutput(List<Delivery> deliveries) {
        return deliveries.stream().map(this::toOutput).collect(Collectors.toList());
    }

    public Delivery toEntity(DeliveryInput deliveryInput) {
        return modelMapper.map(deliveryInput,  Delivery.class);
    }
}
