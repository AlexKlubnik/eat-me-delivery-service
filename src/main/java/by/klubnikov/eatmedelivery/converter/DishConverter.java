package by.klubnikov.eatmedelivery.converter;

import by.klubnikov.eatmedelivery.dto.DishDto;
import by.klubnikov.eatmedelivery.dto.DishListView;
import by.klubnikov.eatmedelivery.entity.Dish;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        dishDto.setRestaurantName(dish.getRestaurant().getName());
        return dishDto;
    }

    public DishListView convertToListView(Dish dish){
        DishListView dishDto = new DishListView();
        dishDto.setName(dish.getName());
        dishDto.setPrice(dish.getPrice());
        return dishDto;
    }

    public List<DishListView> convertToListView(List <Dish> dishes){
        return dishes.stream()
                .map(this::convertToListView)
                .collect(Collectors.toList());
    }

    public List<Dish> convertListFromDto(List<DishDto> dishes){
        return dishes.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public List<DishDto> convertListToDto(List<Dish> dishes){
        return dishes.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }
}
