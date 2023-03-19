package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.DishConverter;
import by.klubnikov.eatmedelivery.converter.RestaurantConverter;
import by.klubnikov.eatmedelivery.dto.AddressDto;
import by.klubnikov.eatmedelivery.dto.DishDto;
import by.klubnikov.eatmedelivery.dto.RestaurantDto;
import by.klubnikov.eatmedelivery.entity.Dish;
import by.klubnikov.eatmedelivery.entity.Restaurant;
import by.klubnikov.eatmedelivery.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {

    private final RestaurantRepository restaurantRepo;

    private final RestaurantConverter restaurantConverter;

    private final DishService dishService;

    private final DishConverter dishConverter;

    public List<RestaurantDto> findAll() {
        return restaurantRepo.findAll()
                .stream()
                .map(restaurantConverter::convertToDtoWithNameAndAddress)
                .collect(Collectors.toList());
    }

    @Transactional
    public RestaurantDto findById(Long id) {
        return restaurantRepo.findById(id)
                .map(restaurantConverter::convertToDtoWithoutId)
                .orElseThrow();
    }

    @Transactional
    public List<DishDto> findAllDishes(Long restaurantId) {
        return dishService.findDishesByRestaurantId(restaurantId)
                .stream()
                .map(dishConverter::convert)
                .collect(Collectors.toList());
    }

    public RestaurantDto save(RestaurantDto restaurantDto, AddressDto addressDto) {
        restaurantDto.setAddressDto(addressDto);
        Restaurant restaurant = restaurantConverter.convertFromDtoWithNameAndAddress(restaurantDto);
        restaurantRepo.save(restaurant);
        return restaurantConverter.convertToDtoWithoutId(restaurant);
    }


    public void deleteById(Long id) {
        restaurantRepo.deleteById(id);
    }

    @Transactional
    public RestaurantDto saveDish(Long restaurantId, DishDto dishDto) {
        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow();
        Dish dish = dishConverter.convert(dishDto);
        restaurant.addDish(dish);
        restaurantRepo.save(restaurant);
        return restaurantConverter.convertToDtoWithoutId(restaurant);
    }

    @Transactional
    public RestaurantDto deleteDish(Long restaurantId, Long dishId) {
        Dish deletableDish = dishService.findDishesByRestaurantId(restaurantId)
                .stream()
                .filter(dish -> Objects.equals(dish.getId(), dishId))
                .findAny()
                .orElseThrow();

        Restaurant restaurant = restaurantRepo.findById(restaurantId)
                .orElseThrow();

        restaurant.getDishes().remove(deletableDish);
        restaurantRepo.save(restaurant);
        return restaurantConverter.convertToDtoWithoutId(restaurant);
    }
}
