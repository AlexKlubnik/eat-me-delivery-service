package by.klubnikov.eatmedelivery.controller;

import by.klubnikov.eatmedelivery.dto.DishDto;
import by.klubnikov.eatmedelivery.dto.DishListView;
import by.klubnikov.eatmedelivery.dto.RestaurantListView;
import by.klubnikov.eatmedelivery.dto.RestaurantPageView;
import by.klubnikov.eatmedelivery.service.AddressService;
import by.klubnikov.eatmedelivery.service.DishService;
import by.klubnikov.eatmedelivery.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final DishService dishService;


    @GetMapping
    public List<RestaurantListView> findAll() {
        return restaurantService.findAll();
    }

    @GetMapping("{id}")
    public RestaurantPageView findById(@PathVariable Long id) {
        return restaurantService.findById(id);
    }

    @GetMapping("{id}/dishes")
    public List<DishListView> findAllDishes(@PathVariable Long id) {
        return dishService.findAllByRestaurantId(id);
    }

    @GetMapping("{id}/dishes/{dishId}")
    public DishDto findByRestaurantIdAndDishId(@PathVariable Long id, @PathVariable Long dishId) {
        return dishService.findByRestaurantIdAndId(id, dishId);
    }

    @GetMapping("{id}/reviews")
    public List<String> findAllReviews(@PathVariable Long id) {
        return restaurantService.findAllReviews(id);
    }

    @PostMapping("{id}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> saveReview(@PathVariable Long id, @RequestBody String review) {
        return restaurantService.saveReview(id, review);
    }

    @PutMapping("{id}/reviews")
    public String updateReview(@PathVariable Long id, @RequestBody String review, @RequestBody String updatedReview){
        return restaurantService.updateReview(id, review, updatedReview);
    }

    @DeleteMapping("{id}/reviews")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long id, @RequestBody String review) {
        restaurantService.deleteReview(id, review);
    }

}
