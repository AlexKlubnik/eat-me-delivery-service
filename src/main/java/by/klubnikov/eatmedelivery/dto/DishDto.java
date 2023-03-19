package by.klubnikov.eatmedelivery.dto;

import lombok.Data;

@Data
public class DishDto {

    private String name;

    private String description;

    private double price;

    private Long restaurantId;

    private String restaurantName;

}
