package by.klubnikov.eatmedelivery.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RestaurantDto {
    private String name="";

    private AddressDto address = new AddressDto();

    private String description="";

    private List<DishDto> dishes = new ArrayList<>();

    private List<String> reviews = new ArrayList<>();
}
