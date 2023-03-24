package by.klubnikov.eatmedelivery.dto;

import lombok.Data;

@Data
public class DishDto {

    private String name="";

    private String description="";

    private double price=0.0;

    private Long restaurantId=0L;

    private String restaurantName="";

}
