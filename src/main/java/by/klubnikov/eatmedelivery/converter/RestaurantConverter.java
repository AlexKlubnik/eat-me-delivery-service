package by.klubnikov.eatmedelivery.converter;

import by.klubnikov.eatmedelivery.dto.RestaurantDto;
import by.klubnikov.eatmedelivery.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RestaurantConverter {

    private final DishConverter dishConverter;

    private final AddressConverter addressConverter;

    public RestaurantDto convertToDto(Restaurant restaurant){
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setAddress(addressConverter.convertToDto(restaurant.getAddress()));
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setDishes(dishConverter.convertToDto(restaurant.getDishes()));
        restaurantDto.setReviews(restaurant.getReviews());
        return restaurantDto;
    }


    public Restaurant convertFromDto(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(addressConverter.convertFromDto(restaurantDto.getAddress()));
        restaurant.setDescription(restaurantDto.getDescription());
        restaurant.setDishes(dishConverter.convertFromDto(restaurantDto.getDishes()));
        restaurant.setReviews(restaurantDto.getReviews());
        return restaurant;
    }

    public List<RestaurantDto> convertToDto(List<Restaurant> restaurants){
        return restaurants.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<Restaurant> convertFromDto(List<RestaurantDto> restaurants){
        return restaurants.stream()
                .map(this::convertFromDto)
                .collect(Collectors.toList());
    }
}

