package by.klubnikov.eatmedelivery.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RestaurantDto {
    private String name;

    private AddressDto address;

    private String description;

    private List<DishDto> dishes = new ArrayList<>();

    private List<String> reviews = new ArrayList<>();
}
