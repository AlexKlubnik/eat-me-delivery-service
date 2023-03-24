package by.klubnikov.eatmedelivery.controller;

import by.klubnikov.eatmedelivery.dto.AddressDto;
import by.klubnikov.eatmedelivery.dto.DishDto;
import by.klubnikov.eatmedelivery.dto.RestaurantDto;
import by.klubnikov.eatmedelivery.service.AddressService;
import by.klubnikov.eatmedelivery.service.DishService;
import by.klubnikov.eatmedelivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("restaurants-manager")
@Slf4j
public class RestaurantMangerController {

    private final RestaurantService restaurantService;

    private final DishService dishService;

    private final AddressService addressService;

    @GetMapping
    public List<RestaurantDto> findAll() {
        return restaurantService.findAll();
    }

    @GetMapping("{id}")
    public RestaurantDto findById(@PathVariable Long id) {
        return restaurantService.findById(id);
    }

    @PostMapping
    public RestaurantDto save(RestaurantDto restaurantDto) {
        return restaurantService.save(restaurantDto);
    }

    @PutMapping("{id}")
    public RestaurantDto update(@PathVariable Long id, RestaurantDto restaurantDto) {
        return restaurantService.update(id, restaurantDto);
    }

    @DeleteMapping
    public void deleteById(Long id) {
        restaurantService.deleteById(id);
    }

    @GetMapping("{id}/dishes")
    public List<DishDto> findAllDishes(@PathVariable Long id) {
        return dishService.findAllByRestaurantId(id);
    }


    @PutMapping("{id}/dishes")
    public RestaurantDto saveDish(@PathVariable Long id, DishDto dishDto) {
        return restaurantService.saveDish(id, dishDto);
    }

    @PutMapping("{id}/dishes/{dishId}")
    public DishDto updateDish(@PathVariable Long id, @PathVariable Long dishId, DishDto dishDto) {
        return dishService.update(dishId, dishDto);
    }

    @DeleteMapping("{id}/dishes")
    public void deleteDish(@PathVariable Long id, @RequestParam Long dishId) {
        dishService.deleteById(dishId);
    }


    @PutMapping("{id}/address")
    public RestaurantDto saveAddress(@PathVariable Long id, AddressDto address) {
        return restaurantService.saveAddress(id, address);
    }

    @PutMapping("{id}/address/{addressId}")
    public AddressDto updateAddress(@PathVariable Long id, @PathVariable Long addressId, AddressDto address) {
        return addressService.save(addressId, address);
    }

}
