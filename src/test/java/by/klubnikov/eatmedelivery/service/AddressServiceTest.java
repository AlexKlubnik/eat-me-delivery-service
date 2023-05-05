package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.AddressConverter;
import by.klubnikov.eatmedelivery.dto.AddressDto;
import by.klubnikov.eatmedelivery.entity.Address;
import by.klubnikov.eatmedelivery.error.ResourceNotFoundException;
import by.klubnikov.eatmedelivery.repository.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceTest {
    private AddressRepository repository;
    private AddressService service;
    private AddressDto addressDto;
    private Address address;

    @BeforeEach
    void setUp() {
        repository = mock(AddressRepository.class);
        AddressConverter converter = new AddressConverter();
        service = spy(new AddressService(repository, converter));
        initAddress();
        initAddressDto();
    }

    private void initAddress() {
        address = new Address();
        address.setId(1L);
        address.setCity("Gomel");
        address.setStreet("Malinovka");
        address.setHouseNumber(12);
        address.setBuildingNumber(0);
        address.setApartmentNumber(22);
    }

    private void initAddressDto() {
        addressDto = new AddressDto();
        addressDto.setCity("Gomel");
        addressDto.setStreet("Malinovka");
        addressDto.setHouseNumber(12);
        addressDto.setBuildingNumber(0);
        addressDto.setApartmentNumber(22);
    }

    @Test
    void save_WhenAddressExists() {
        when(repository.existsById(1L)).thenReturn(true);
        when(repository.save(any(Address.class))).thenReturn(address);
        AddressDto expected = service.save(1L, addressDto);
        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).save(any(Address.class));
        verifyNoMoreInteractions(repository);
        assertEquals(expected, addressDto);
    }

    @Test
    void save_WhenAddressNotExists() {
        when(repository.existsById(1L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> service.save(1L, addressDto));
    }
}