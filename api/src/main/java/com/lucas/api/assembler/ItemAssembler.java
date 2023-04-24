package com.lucas.api.assembler;

import com.lucas.api.dtos.input.ItemInput;
import com.lucas.api.dtos.output.ItemOutput;
import com.lucas.domain.model.Item;
import org.modelmapper.ModelMapper;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ItemAssembler {

    ModelMapper modelMapper = new ModelMapper();

    public ItemOutput toOutput(Item item) {
        return modelMapper.map(item, ItemOutput.class);
    }

    public List<ItemOutput> toCollectionOutput(List<Item> itens) {
        return itens.stream().map(this::toOutput).collect(Collectors.toList());
    }

    public Item toEntity(ItemInput itemInput) {
        return modelMapper.map(itemInput, Item.class);
    }
}
