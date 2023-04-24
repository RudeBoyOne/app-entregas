package com.lucas.domain.service.addressee;

import com.lucas.common.exceptions.ExistingResourceException;
import com.lucas.common.exceptions.ResourceNotFoundException;
import com.lucas.common.exceptions.ResourceNotSaveException;
import com.lucas.domain.model.Addressee;
import com.lucas.domain.model.Delivery;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AddresseeService {

    @Transactional
    public Addressee createAddresse(Addressee addressee) {
        boolean existAdresse = Addressee.findByCepAndHouseNumber(addressee.getCep(), addressee.getHouseNumber())
                .stream().anyMatch(existingAddressee -> !existingAddressee.equals(addressee));

        if (existAdresse) {
            throw new ExistingResourceException("There is already a addressee with cep: " + addressee.getCep()
                    + " and with house number: " + addressee.getHouseNumber());
        }

        Addressee.persist(addressee);
        Optional<Addressee> addresseeSave = Addressee.findByCepAndHouseNumber(addressee.getCep(),
                addressee.getHouseNumber());

        if (addresseeSave.isEmpty()) {
            throw new ResourceNotSaveException("Addressee with cep: " + addressee.getCep() + " and with house number:" +
                    " " + addressee.getHouseNumber() + " not saved, try again!");
        }

        return addresseeSave.get();
    }

    @Transactional
    public Addressee updateAddressee(UUID id, Addressee addressee) {
        Optional<Addressee> addresseeExisted = Addressee.findByIdOptional(id);

        if (addresseeExisted.isPresent()) {
            Addressee addresseeToUpdate = addresseeExisted.get();
            addresseeToUpdate.setName(addressee.getName());
            addresseeToUpdate.setCity(addressee.getCity());
            addresseeToUpdate.setState(addressee.getState());
            addresseeToUpdate.setCep(addressee.getCep());
            addresseeToUpdate.setHouseNumber(addressee.getHouseNumber());
            addresseeToUpdate.setNeighborhood(addressee.getNeighborhood());
            addresseeToUpdate.setStreet(addressee.getStreet());
            addresseeToUpdate.setLongitude(addressee.getLongitude());
            addresseeToUpdate.setLatitude(addressee.getLatitude());
            addresseeToUpdate.persist();
            return addresseeToUpdate;
        } else {
            throw new ResourceNotFoundException("Addressee with id: " + id + " does not exist!");
        }
    }

    public List<Addressee> listAll() {
        return Addressee.listAll();
    }

    public Addressee findAddresseeById(UUID id) {
        Optional<Addressee> addressee = Addressee.findByIdOptional(id);

        if (addressee.isEmpty()) {
            throw new ResourceNotFoundException("Addressee with id: " + id + " does not exist!");
        }

        return addressee.get();
    }

    @Transactional
    public void deleteAddressee(UUID id) {
        Addressee addressee = findAddresseeById(id);

        List<Delivery> addressees = Delivery.findByAddressee(addressee);

        if (!addressees.isEmpty()) {
            throw new ExistingResourceException("Addressee contains deliveries in system. Deletion aborted!");
        }

        addressee.delete();
    }
}
