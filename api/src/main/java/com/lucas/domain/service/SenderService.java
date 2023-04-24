package com.lucas.domain.service;

import com.lucas.common.exceptions.ExistingResourceException;
import com.lucas.common.exceptions.ResourceNotFoundException;
import com.lucas.common.exceptions.ResourceNotSaveException;
import com.lucas.domain.model.Delivery;
import com.lucas.domain.model.Item;
import com.lucas.domain.model.Sender;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class SenderService {

    @Transactional
    public Sender createSender(Sender sender) {
        boolean existSender = Sender.findByContract(sender.getContract())
                .stream().anyMatch(existingSender -> !existingSender.equals(sender));

        if (existSender) {
            throw new ExistingResourceException("There is already a sender with contract: " + sender.getContract());
        }

        Sender.persist(sender);
        Optional<Sender> senderSave = Sender.findByContract(sender.getContract());

        if (senderSave.isEmpty()) {
            throw new ResourceNotSaveException("Sender with contract: "+ sender.getContract() + " not saved, try again!");
        }

        return senderSave.get();
    }

    @Transactional
    public Sender updateSender(UUID id, Sender sender) {
        Boolean existsed = existsSenderById(id);

        if(existsed) {
            Sender senderExistsed = findSenderById(id);
            senderExistsed.setName(sender.getName());
            senderExistsed.setContract(sender.getContract());
            senderExistsed.setCpf(sender.getCpf());
            senderExistsed.persist();
            return senderExistsed;
        } else {
            throw new ResourceNotFoundException("Sender with id: " + id + " does not exist!");
        }

    }

    public List<Sender> listAll() {
        return Sender.listAll();
    }

    public Sender findSenderById(UUID id) {
        Optional<Sender> sender = Sender.findById(id);

        if (sender.isEmpty()){
            throw new ResourceNotFoundException("Sender with id: " + id + " does not exist!");
        }

        return sender.get();
    }

    public Boolean existsSenderById(UUID id) {
        return Sender.existsById(id);
    }

    @Transactional
    public void deleteSender(UUID id) {
        Sender sender = findSenderById(id);

        List<Item> itemsByOwner = Item.listByOwner(findSenderById(id));
        List<Delivery> senders = Delivery.findBySender(sender);

        if (!itemsByOwner.isEmpty() || !senders.isEmpty()){
            throw new ExistingResourceException("Sender contains items or deliveries in system. Deletion aborted!");
        }

        sender.deleteById(id);

    }
}
