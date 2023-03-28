package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.AddressConverter;
import by.klubnikov.eatmedelivery.dto.AddressDto;
import by.klubnikov.eatmedelivery.entity.Address;
import by.klubnikov.eatmedelivery.repository.AddressRepository;
import by.klubnikov.eatmedelivery.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository repository;
    private final AddressConverter converter;


    public AddressDto findById(Long id) {
        Address address = repository.findById(id).orElseThrow();
        return converter.convert(address);
    }


    public AddressDto save(Long id, AddressDto addressDto) {
        AddressDto returnableAddress = null;
        if (repository.findById(id).isPresent()) {
            Address address = converter.convert(addressDto);
            address.setId(id);
            returnableAddress=converter.convert(repository.save(address));
        }
        return returnableAddress;
    }
}
