package by.klubnikov.eatmedelivery.dto;

import lombok.Data;

@Data
//@AllArgsConstructor
public class AddressDto {
    private String city;

    private String street;

    private int houseNumber;

    private int buildingNumber;

    private int apartmentNumber;

}
