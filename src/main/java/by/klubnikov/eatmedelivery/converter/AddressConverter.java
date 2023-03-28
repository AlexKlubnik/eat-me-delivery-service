package by.klubnikov.eatmedelivery.converter;

import by.klubnikov.eatmedelivery.dto.AddressDto;
import by.klubnikov.eatmedelivery.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {

    public Address convert(AddressDto addressDto){
        Address address = new Address();
        address.setCity(addressDto.getCity());
        address.setStreet(addressDto.getStreet());
        address.setHouseNumber(addressDto.getHouseNumber());
        address.setBuildingNumber(addressDto.getBuildingNumber());
        address.setApartmentNumber(addressDto.getApartmentNumber());
        return address;
    }

    public AddressDto convert(Address address){
        AddressDto addressDto = new AddressDto();
        addressDto.setCity(address.getCity());
        addressDto.setStreet(address.getStreet());
        addressDto.setHouseNumber(address.getHouseNumber());
        addressDto.setBuildingNumber(address.getBuildingNumber());
        addressDto.setApartmentNumber(address.getApartmentNumber());
        return addressDto;
    }
}
