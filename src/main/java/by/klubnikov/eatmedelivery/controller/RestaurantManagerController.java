package by.klubnikov.eatmedelivery.controller;

import by.klubnikov.eatmedelivery.dto.*;
import by.klubnikov.eatmedelivery.service.AddressService;
import by.klubnikov.eatmedelivery.service.DishService;
import by.klubnikov.eatmedelivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public List<RestaurantListView> findAll() {
        return restaurantService.findAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantPageView findById(@PathVariable Long id) {
        return restaurantService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantListView save(RestaurantPageView restaurant, AddressDto address) {
        return restaurantService.save(restaurant, address);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantPageView update(@PathVariable Long id, RestaurantPageView restaurant, AddressDto address) {
        return restaurantService.save(id, restaurant, address);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        restaurantService.deleteById(id);
    }

    @GetMapping("{id}/dishes")
    @ResponseStatus(HttpStatus.OK)
    public List<DishListView> findAllDishes(@PathVariable Long id) {
        return dishService.findAllByRestaurantId(id);
    }


    @PostMapping("{id}/dishes")
    @ResponseStatus(HttpStatus.CREATED)
    public DishListView saveDish(@PathVariable Long id, DishDto dishDto) {
        return dishService.save(id, dishDto);
    }

    @GetMapping("{id}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.OK)
    public DishDto findByRestaurantIdAndDishId(@PathVariable Long id, @PathVariable Long dishId) {
        return dishService.findByRestaurantIdAndId(id, dishId);
    }

    @PutMapping("{id}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.OK)
    public DishDto updateDish(@PathVariable Long id, @PathVariable Long dishId, DishDto dishDto) {
        return dishService.save(id, dishId, dishDto);
    }

    @DeleteMapping("{id}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(@PathVariable Long id, @PathVariable Long dishId) {
        dishService.deleteByRestaurantIdAndId(id, dishId);
    }

    @GetMapping ("{id}/reviews")
    @ResponseStatus(HttpStatus.OK)
    public List<String> findAllReviews(@PathVariable Long id){
        return restaurantService.findAllReviews(id);
    }

    @DeleteMapping ("{id}/reviews")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long id, @RequestBody String review){
        restaurantService.deleteReview(id, review);
    }

}
