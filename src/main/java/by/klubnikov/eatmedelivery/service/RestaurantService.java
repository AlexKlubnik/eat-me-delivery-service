package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.DishConverter;
import by.klubnikov.eatmedelivery.converter.RestaurantConverter;
import by.klubnikov.eatmedelivery.dto.*;
import by.klubnikov.eatmedelivery.entity.Address;
import by.klubnikov.eatmedelivery.entity.Restaurant;
import by.klubnikov.eatmedelivery.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantService {

    private final RestaurantRepository repository;

    private final RestaurantConverter converter;

    private final AddressService addressService;

    public List<RestaurantListView> findAll() {
        List<Restaurant> restaurants = repository.findAll();
        return converter.convertToListView(restaurants);
    }

    public RestaurantPageView findById(Long id) {
        return repository.findById(id)
                .map(converter::convertToPageView)
                .orElseThrow();
    }


    public RestaurantListView save(RestaurantPageView restaurant, AddressDto address) {
        restaurant.setAddress(address);
        Restaurant restaurantToDb = converter.convertFromPageView(restaurant);
        Restaurant savedRestaurant = repository.save(restaurantToDb);
        return converter.convertToListView(savedRestaurant);
    }


    public RestaurantPageView save(Long id, RestaurantPageView restaurant, AddressDto address) {
        Restaurant restaurantFromDb = repository.findById(id).orElseThrow();
        Long addressId = restaurantFromDb.getAddress().getId();
        addressService.save(addressId, address);
        if (!restaurant.getName().equals(restaurantFromDb.getName()))
            restaurantFromDb.setName(restaurant.getName());
        if (!restaurant.getDescription().equals(restaurantFromDb.getDescription()))
            restaurantFromDb.setDescription(restaurant.getDescription());
        Restaurant savedRestaurant = repository.save(restaurantFromDb);
        return converter.convertToPageView(savedRestaurant);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public List<String> findAllReviews(Long id) {
        return repository.findById(id)
                .orElseThrow().getReviews();
    }

    @Transactional
    public void deleteReview(Long id, String review) {
        Restaurant restaurant = repository.findById(id).orElseThrow();
        if (restaurant.getReviews()
                .stream()
                .anyMatch(r -> r.equals(review))) {
            restaurant.getReviews().remove(review);
        }
    }

}
