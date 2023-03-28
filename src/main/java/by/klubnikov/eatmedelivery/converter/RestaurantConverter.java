package by.klubnikov.eatmedelivery.converter;

import by.klubnikov.eatmedelivery.dto.RestaurantDto;
import by.klubnikov.eatmedelivery.dto.RestaurantListView;
import by.klubnikov.eatmedelivery.dto.RestaurantPageView;
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

    public RestaurantDto convert(Restaurant restaurant){
        RestaurantDto restaurantDto = new RestaurantDto();
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setAddress(addressConverter.convert(restaurant.getAddress()));
        restaurantDto.setDescription(restaurant.getDescription());
        restaurantDto.setDishes(dishConverter.convertListToDto(restaurant.getDishes()));
        restaurantDto.setReviews(restaurant.getReviews());
        return restaurantDto;
    }

    public RestaurantListView convertToListView(Restaurant restaurant){
        RestaurantListView restaurantDto = new RestaurantListView();
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setAddress(addressConverter.convert(restaurant.getAddress()));
        return restaurantDto;
    }

    public List<RestaurantListView> convertToListView(List <Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::convertToListView)
                .collect(Collectors.toList());
    }

    public RestaurantPageView convertToPageView(Restaurant restaurant){
        RestaurantPageView restaurantDto = new RestaurantPageView();
        restaurantDto.setName(restaurant.getName());
        restaurantDto.setAddress(addressConverter.convert(restaurant.getAddress()));
        restaurantDto.setDescription(restaurant.getDescription());
        return restaurantDto;
    }

    public Restaurant convertFromPageView(RestaurantPageView restaurantDto){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(addressConverter.convert(restaurantDto.getAddress()));
        restaurant.setDescription(restaurantDto.getDescription());
        return restaurant;
    }

    public Restaurant convert(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(addressConverter.convert(restaurantDto.getAddress()));
        restaurant.setDescription(restaurantDto.getDescription());
        restaurant.setDishes(dishConverter.convertListFromDto(restaurantDto.getDishes()));
        restaurant.setReviews(restaurantDto.getReviews());
        return restaurant;
    }

    public List<RestaurantDto> convert(List<Restaurant> restaurants){
        return restaurants.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

}

