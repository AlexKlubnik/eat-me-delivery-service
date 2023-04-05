package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.AddressConverter;
import by.klubnikov.eatmedelivery.dto.AddressDto;
import by.klubnikov.eatmedelivery.entity.Address;
import by.klubnikov.eatmedelivery.repository.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AddressServiceTest {

    @Mock
    private AddressRepository repository;
    @Spy
    private AddressService service;
    private AddressDto addressDto;
    private Address address;
    private Address addressWithoutId;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(AddressRepository.class);
        AddressConverter converter = new AddressConverter();
        service = Mockito.spy(new AddressService(repository, converter));

        addressDto = new AddressDto();
        addressDto.setCity("Brest");
        addressDto.setStreet("Berezovka");
        addressDto.setHouseNumber(11);
        addressDto.setBuildingNumber(1);
        addressDto.setApartmentNumber(14);

        address = new Address();
        address.setId(1L);
        address.setCity("Brest");
        address.setStreet("Berezovka");
        address.setHouseNumber(11);
        address.setBuildingNumber(1);
        address.setApartmentNumber(14);

        addressWithoutId = new Address();
        addressWithoutId.setId(1L);
        addressWithoutId.setCity("Brest");
        addressWithoutId.setStreet("Berezovka");
        addressWithoutId.setHouseNumber(11);
        addressWithoutId.setBuildingNumber(1);
        addressWithoutId.setApartmentNumber(14);
    }

    @Test
    void save() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(address));
        Mockito.when(repository.save(addressWithoutId)).thenReturn(address);
        AddressDto expected = service.save(1L, addressDto);
        Mockito.verify(repository, Mockito.times(1)).findById(1L);
        Mockito.verify(repository, Mockito.times(1)).save(address);
        Assertions.assertNotNull(expected);
        Assertions.assertEquals(expected, addressDto);
    }
}