package com.lucas.domain.service;

import com.lucas.common.exceptions.ExistingResourceException;
import com.lucas.common.exceptions.ResourceNotFoundException;
import com.lucas.common.exceptions.ResourceNotSaveException;
import com.lucas.domain.model.Delivery;
import com.lucas.domain.model.Item;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ItemService {

    @Transactional
    public Item createItem(Item item) {
        boolean existItem = Item.findByCode(item.getCode())
                .stream().anyMatch(existingItem -> !existingItem.equals(item));

        if (existItem) {
            throw new ExistingResourceException("There is already a item with code: " + item.getCode());
        }

        Item.persist(item);
        Optional<Item> itemSave = Item.findByCode(item.getCode());

        if (itemSave.isEmpty()) {
            throw new ResourceNotSaveException("Item with code: "+ item.getCode() + " not saved, try again!");
        }

        return itemSave.get();
    }

    @Transactional
    public Item updateItem(UUID id, Item item) {
        Optional<Item> itemExisted = Item.findByIdOptional(id);

        if (itemExisted.isPresent()) {
            Item itemToUpdate = itemExisted.get();
            itemToUpdate.setCode(item.getCode());
            itemToUpdate.setTypeItem(item.getTypeItem());
            itemToUpdate.setOwner(item.getOwner());
            itemToUpdate.persist();
            return itemToUpdate;
        } else {
            throw new ResourceNotFoundException("Item with id: " + id + " does not exist!");
        }
    }

    public List<Item> listAll() {
        return Item.listAll();
    }

    public Item findItemById(UUID id) {
        Optional<Item> item = Item.findByIdOptional(id);

        if (item.isEmpty()) {
            throw new ResourceNotFoundException("Item with id: " + id + " does not exist!");
        }

        return item.get();
    }

    @Transactional
    public void deleteItem(UUID id) {
        Item item = findItemById(id);

        Optional<Delivery> delivery = Delivery.findByItem(item);

        if (delivery.isPresent()) {
            throw new ExistingResourceException("Item contains deliveries in system. Deletion aborted!");
        }

        item.delete();
    }


}
