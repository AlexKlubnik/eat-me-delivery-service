package by.klubnikov.eatmedelivery.controller;

import by.klubnikov.eatmedelivery.dto.*;
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
public class RestaurantManagerController {

    private final RestaurantService restaurantService;

    private final DishService dishService;

    private final AddressService addressService;

    @GetMapping
    public List<RestaurantListView> findAll() {
        return restaurantService.findAll();
    }

    @GetMapping("{id}")
    public RestaurantPageView findById(@PathVariable Long id) {
        return restaurantService.findById(id);
    }

    @PostMapping
    public RestaurantListView save(RestaurantPageView restaurant, AddressDto address) {
        return restaurantService.save(restaurant, address);
    }

    @PutMapping("{id}")
    public RestaurantPageView update(@PathVariable Long id, RestaurantPageView restaurant, AddressDto address) {
        return restaurantService.save(id, restaurant, address);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        restaurantService.deleteById(id);
    }

    @GetMapping("{id}/dishes")
    public List<DishListView> findAllDishes(@PathVariable Long id) {
        return dishService.findAllByRestaurantId(id);
    }


    @PostMapping("{id}/dishes")
    public DishListView saveDish(@PathVariable Long id, DishDto dishDto) {
        return dishService.save(id, dishDto);
    }

    @GetMapping("{id}/dishes/{dishId}")
    public DishDto findByRestaurantIdAndDishId(@PathVariable Long id, @PathVariable Long dishId) {
        return dishService.findByRestaurantIdAndId(id, dishId);
    }

    @PutMapping("{id}/dishes/{dishId}")
    public DishDto updateDish(@PathVariable Long id, @PathVariable Long dishId, DishDto dishDto) {
        return dishService.save(id, dishId, dishDto);
    }

    @DeleteMapping("{id}/dishes/{dishId}")
    public void deleteDish(@PathVariable Long id, @PathVariable Long dishId) {
        dishService.deleteByRestaurantIdAndId(id, dishId);
    }

    @GetMapping ("{id}/reviews")
    public List<String> findAllReviews(@PathVariable Long id){
        return restaurantService.findAllReviews(id);
    }

    @DeleteMapping ("{id}/reviews")
    public void deleteReview(@PathVariable Long id, @RequestBody String review){
        restaurantService.deleteReview(id, review);
    }

}
