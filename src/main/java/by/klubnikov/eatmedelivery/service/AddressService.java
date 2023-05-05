package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.AddressConverter;
import by.klubnikov.eatmedelivery.dto.AddressDto;
import by.klubnikov.eatmedelivery.entity.Address;
import by.klubnikov.eatmedelivery.error.ResourceNotFoundException;
import by.klubnikov.eatmedelivery.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository repository;
    private final AddressConverter converter;

    public AddressDto save(Long id, AddressDto addressDto) {
        if (repository.existsById(id)) {
            Address address = converter.convert(addressDto);
            address.setId(id);
            return converter.convert(repository.save(address));
        } else throw new ResourceNotFoundException("Address with id " + id + "not found");
    }
}
