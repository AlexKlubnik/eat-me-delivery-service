package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.AddressConverter;
import by.klubnikov.eatmedelivery.converter.DishConverter;
import by.klubnikov.eatmedelivery.converter.RestaurantConverter;
import by.klubnikov.eatmedelivery.dto.AddressDto;
import by.klubnikov.eatmedelivery.dto.DishDto;
import by.klubnikov.eatmedelivery.dto.RestaurantDto;
import by.klubnikov.eatmedelivery.entity.Address;
import by.klubnikov.eatmedelivery.entity.Dish;
import by.klubnikov.eatmedelivery.entity.Restaurant;
import by.klubnikov.eatmedelivery.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {

    private final RestaurantRepository repository;

    private final RestaurantConverter converter;

    private final DishService dishService;

    private final DishConverter dishConverter;

    private final AddressConverter addressConverter;

    private final AddressService addressService;

    public List<RestaurantDto> findAll() {
        List<Restaurant> restaurants = repository.findAll();
        return converter.convertToDto(restaurants);
    }

    public RestaurantDto findById(Long id) {
        return repository.findById(id)
                .map(converter::convertToDto)
                .orElseThrow();
    }

    public RestaurantDto save(RestaurantDto restaurantDto) {
        Restaurant restaurantToDb = converter.convertFromDto(restaurantDto);
        Restaurant restaurant = repository.save(restaurantToDb);
        return converter.convertToDto(restaurant);
    }

    public RestaurantDto update(Long id, RestaurantDto restaurantDto) {
        Restaurant restaurantFromDb = repository.findById(id).orElseThrow();
        if (!restaurantDto.getName().isBlank())
            restaurantFromDb.setName(restaurantDto.getName());
        if (!restaurantDto.getDescription().isBlank())
            restaurantFromDb.setDescription(restaurantDto.getDescription());
        Restaurant restaurant = repository.save(restaurantFromDb);
        return converter.convertToDto(restaurant);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public RestaurantDto saveDish(Long id, DishDto dishDto) {
        Restaurant restaurantFromDb = repository.findById(id)
                .orElseThrow();
        Dish dish = dishConverter.convertFromDto(dishDto);
        restaurantFromDb.addDish(dish);
        return converter.convertToDto(restaurantFromDb);
    }


    public RestaurantDto saveAddress(Long id, AddressDto addressDto) {
        Restaurant restaurantFromDb = repository.findById(id)
                .orElseThrow();
        Address address = addressService.save(addressDto);
        restaurantFromDb.setAddress(address);
        return converter.convertToDto(repository.save(restaurantFromDb));
    }

}
