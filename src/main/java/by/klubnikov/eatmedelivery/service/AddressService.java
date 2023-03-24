package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.AddressConverter;
import by.klubnikov.eatmedelivery.dto.AddressDto;
import by.klubnikov.eatmedelivery.entity.Address;
import by.klubnikov.eatmedelivery.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository repository;
    private final AddressConverter converter;

    public AddressDto findById(Long id) {
        Address address = repository.findById(id).orElseThrow();
        return converter.convertToDto(address);
    }

    public Address save(AddressDto addressDto) {
        Address address = converter.convertFromDto(addressDto);
        return repository.save(address);
    }

    public AddressDto save(Long id, AddressDto addressDto) {
        Address address = converter.convertFromDto(addressDto);
        address.setId(id);
        return converter.convertToDto(repository.save(address));
    }


}
