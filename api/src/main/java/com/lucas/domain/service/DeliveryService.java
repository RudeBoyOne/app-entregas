package com.lucas.domain.service;

import com.lucas.common.exceptions.ExistingResourceException;
import com.lucas.common.exceptions.ResourceNotFoundException;
import com.lucas.common.exceptions.ResourceNotSaveException;
import com.lucas.domain.model.Addressee;
import com.lucas.domain.model.Delivery;
import com.lucas.domain.model.Item;
import com.lucas.domain.model.Sender;
import com.lucas.domain.model.enums.StatusDelivery;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class DeliveryService {

    @Transactional
    public Delivery createDelivery(Delivery delivery) {
        boolean existDelivery = Delivery.findByItem(delivery.getItem())
                .stream().anyMatch(existingDelivery -> !existingDelivery.equals(delivery));

        if (existDelivery) {
            throw new ExistingResourceException("There is already a delivery with item: " + delivery.getItem());
        }

        delivery.setStatus(StatusDelivery.PENDING);
        delivery.setShippingDate(LocalDateTime.now());

        delivery.persist();
        Optional<Delivery> deliverySave = Delivery.findByItem(delivery.getItem());

        if (deliverySave.isEmpty()) {
            throw new ResourceNotSaveException("Item with code: "+ delivery.getItem() + " not saved, try again!");
        }

        return deliverySave.get();
    }


    @Transactional
    public Delivery finalizeDelivery(UUID id) {
        Delivery delivery = findDeliveryById(id);

        if (!delivery.getStatus().equals(StatusDelivery.PENDING)) {
            throw new ResourceNotSaveException("Delivery cannot be finalize!");
        }

        delivery.setStatus(StatusDelivery.FINISHED);
        delivery.setDeliveryDate(LocalDateTime.now());
        delivery.persist();
        return delivery;
    }

    @Transactional
    public Delivery cancelDelivery(UUID id) {
        Delivery delivery = findDeliveryById(id);

        if (!delivery.getStatus().equals(StatusDelivery.PENDING)) {
            throw new ResourceNotSaveException("Delivery cannot be canceled!");
        }

        delivery.setStatus(StatusDelivery.CANCELED);
        delivery.setCancellationDate(LocalDateTime.now());
        delivery.persist();
        return delivery;
    }

    public Delivery findDeliveryById(UUID id) {
        Optional<Delivery> existDelivery = Delivery.findByIdOptional(id);

        if (existDelivery.isEmpty()) {
            throw new ResourceNotFoundException("Delivery with id: " + id + ", does not exist!");
        }

        return existDelivery.get();
    }

    public Delivery findDeliveryByItem(Item item) {
        Optional<Delivery> delivery = Delivery.findByItem(item);

        if (delivery.isEmpty()) {
            throw new ResourceNotFoundException("Deliveries of item: " + item.getCode() + ", does not exist!");
        }

        return delivery.get();
    }

    public List<Delivery> findDeliveryBySender(Sender sender) {
        List<Delivery> deliveries = Delivery.findBySender(sender);

        if (deliveries.isEmpty()) {
            throw new ResourceNotFoundException("Deliveries of sender: " + sender.getName() + ", does not exist!");
        }

        return deliveries;
    }

    public List<Delivery> findDeliveryByAddressee(Addressee addressee) {
        List<Delivery> deliveries = Delivery.findByAddressee(addressee);

        if (deliveries.isEmpty()) {
            throw new ResourceNotFoundException("Deliveries of addressee: " + addressee.getName() + ", does not " +
                    "exist!");
        }

        return deliveries;
    }

    public List<Delivery> listAll() {
        return Delivery.listAll();
    }

    @Transactional
    public void deleteDelivery(UUID id) {
        Delivery delivery = findDeliveryById(id);
        delivery.delete();
    }
}
