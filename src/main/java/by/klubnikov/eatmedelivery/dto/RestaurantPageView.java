package by.klubnikov.eatmedelivery.dto;

import lombok.Data;

@Data
public class RestaurantPageView {
    private String name;

    private AddressDto address;

    private String description;
}
