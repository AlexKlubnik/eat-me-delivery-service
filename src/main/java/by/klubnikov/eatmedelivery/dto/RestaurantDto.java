package by.klubnikov.eatmedelivery.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class RestaurantDto {
    private String name;

    private AddressDto addressDto;

    private List<DishDto> dishDtos;

    private List<String> reviews;
}
