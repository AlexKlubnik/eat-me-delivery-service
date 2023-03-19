package by.klubnikov.eatmedelivery.converter;

import by.klubnikov.eatmedelivery.dto.RestaurantDto;
import by.klubnikov.eatmedelivery.entity.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RestaurantConverter {

    private final DishConverter dishConverter;

    private final AddressConverter addressConverter;


    /**
     * This restaurant DTO is using for restaurant's page
     *
     * @param restaurant - restaurant entity obtained from DB
     * @return restaurant DTO without Id field
     */
    public RestaurantDto convertToDtoWithoutId(Restaurant restaurant) {
        return RestaurantDto.builder()
                .name(restaurant.getName())
                .addressDto(addressConverter.convert(restaurant.getAddress()))
                .dishDtos(restaurant.getDishes()
                        .stream()
                        .map(dishConverter::convert)
                        .collect(Collectors.toList()))
                .reviews(restaurant.getReviews())
                .build();
    }

    /**
     * This restaurant DTO is using for restaurants list page
     *
     * @param restaurant - restaurant entity obtained from DB
     * @return restaurant DTO with name and address
     */
    public RestaurantDto convertToDtoWithNameAndAddress(Restaurant restaurant) {
        return RestaurantDto.builder()
                .name(restaurant.getName())
                .addressDto(addressConverter.convert(restaurant.getAddress()))
                .dishDtos(new ArrayList<>())
                .reviews(new ArrayList<>())
                .build();
    }

    public Restaurant convertFromDtoWithNameAndAddress(RestaurantDto restaurantDto){
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(addressConverter.convert(restaurantDto.getAddressDto()));
        return restaurant;
    }

    public Restaurant convertFromDto(RestaurantDto restaurantDto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDto.getName());
        restaurant.setAddress(addressConverter.convert(restaurantDto.getAddressDto()));
        restaurant.setDishes(restaurantDto.getDishDtos()
                .stream()
                .map(dishConverter::convert)
                .collect(Collectors.toList()));
        restaurant.setReviews(restaurantDto.getReviews());
        return restaurant;
    }
}
