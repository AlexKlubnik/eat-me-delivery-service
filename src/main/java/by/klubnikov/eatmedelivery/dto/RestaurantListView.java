package by.klubnikov.eatmedelivery.dto;

import lombok.Data;

@Data
public class RestaurantListView {
    private String name;

    private AddressDto address;
}
