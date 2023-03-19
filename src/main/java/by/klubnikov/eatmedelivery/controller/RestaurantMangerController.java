package by.klubnikov.eatmedelivery.controller;

import by.klubnikov.eatmedelivery.dto.AddressDto;
import by.klubnikov.eatmedelivery.dto.DishDto;
import by.klubnikov.eatmedelivery.dto.RestaurantDto;
import by.klubnikov.eatmedelivery.entity.Address;
import by.klubnikov.eatmedelivery.entity.Restaurant;
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

    @GetMapping
    public List<RestaurantDto> findAll() {
        return restaurantService.findAll();
    }

    @GetMapping("{id}")
    public RestaurantDto findById(@PathVariable Long id) {
        return restaurantService.findById(id);
    }

    @GetMapping("{id}/dishes")
    public List<DishDto> findAllDishes(@PathVariable Long id) {
        return restaurantService.findAllDishes(id);
    }

    @PostMapping
    public RestaurantDto save(RestaurantDto restaurantDto, AddressDto addressDto) {
        return restaurantService.save(restaurantDto, addressDto);
    }

    @PostMapping("{id}/dishes")
    public RestaurantDto saveDish(@PathVariable Long id, DishDto dishDto) {
        return restaurantService.saveDish(id, dishDto);
    }

    @DeleteMapping("{restaurantId}/dishes/{dishId}")
    public RestaurantDto deleteDish(@PathVariable Long restaurantId, @PathVariable Long dishId){
        return restaurantService.deleteDish(restaurantId, dishId);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id){
        restaurantService.deleteById(id);
    }
}
