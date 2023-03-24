package by.klubnikov.eatmedelivery.converter;

import by.klubnikov.eatmedelivery.dto.DishDto;
import by.klubnikov.eatmedelivery.entity.Dish;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DishConverter {

    public Dish convertFromDto(DishDto dishDto){
        Dish dish = new Dish();
        dish.setName(dishDto.getName());
        dish.setDescription(dishDto.getDescription());
        dish.setPrice(dishDto.getPrice());
        return dish;
    }

    public DishDto convertToDto(Dish dish){
        DishDto dishDto = new DishDto();
        dishDto.setName(dish.getName());
        dishDto.setDescription(dish.getDescription());
        dishDto.setPrice(dish.getPrice());
        dishDto.setRestaurantId(dish.getRestaurant().getId());
        dishDto.setRestaurantName(dish.getRestaurant().getName());
        return dishDto;
    }

    public List<Dish> convertFromDto(List<DishDto> dishes){
        return dishes.stream()
                .map(this::convertFromDto)
                .collect(Collectors.toList());
    }

    public List<DishDto> convertToDto(List<Dish> dishes){
        return dishes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}
