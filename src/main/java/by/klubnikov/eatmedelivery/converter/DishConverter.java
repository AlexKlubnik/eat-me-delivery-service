package by.klubnikov.eatmedelivery.converter;

import by.klubnikov.eatmedelivery.dto.DishDto;
import by.klubnikov.eatmedelivery.entity.Dish;
import org.springframework.stereotype.Component;

@Component
public class DishConverter {

    public Dish convert(DishDto dishDto){
        Dish dish = new Dish();
        dish.setName(dishDto.getName());
        dish.setDescription(dishDto.getDescription());
        dish.setPrice(dishDto.getPrice());
        return dish;
    }

    public DishDto convert(Dish dish){
        DishDto dishDto = new DishDto();
        dishDto.setName(dish.getName());
        dishDto.setDescription(dish.getDescription());
        dishDto.setPrice(dish.getPrice());
        dishDto.setRestaurantId(dish.getRestaurant().getId());
        dishDto.setRestaurantName(dish.getRestaurant().getName());
        return dishDto;
    }
}
