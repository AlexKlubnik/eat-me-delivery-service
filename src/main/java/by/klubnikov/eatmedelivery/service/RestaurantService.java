package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.RestaurantConverter;
import by.klubnikov.eatmedelivery.dto.AddressDto;
import by.klubnikov.eatmedelivery.dto.RestaurantListView;
import by.klubnikov.eatmedelivery.dto.RestaurantPageView;
import by.klubnikov.eatmedelivery.dto.UpdateReviewForm;
import by.klubnikov.eatmedelivery.entity.Restaurant;
import by.klubnikov.eatmedelivery.error.ResourceNotFoundException;
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

    private final AddressService addressService;

    public List<RestaurantListView> findAll() {
        List<Restaurant> restaurants = repository.findAll();
        return converter.convertToListView(restaurants);
    }

    public RestaurantPageView findById(Long id) {
        return repository.findById(id)
                .map(converter::convertToPageView)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurant with id " + id + " not found"));
    }


    public RestaurantListView save(RestaurantPageView restaurant, AddressDto address) {
        restaurant.setAddress(address);
        Restaurant restaurantToDb = converter.convertFromPageView(restaurant);
        Restaurant savedRestaurant = repository.save(restaurantToDb);
        return converter.convertToListView(savedRestaurant);
    }


    public RestaurantPageView save(Long id, RestaurantPageView restaurant, AddressDto address) {
        Restaurant restaurantFromDb = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurant with id " + id + " not found"));
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
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurant with id " + id + " not found"))
                .getReviews();
    }

    @Transactional
    public List<String> saveReview(Long id, String review) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurant with id " + id + " not found"));
        List<String> reviews = restaurant.getReviews();
        reviews.add(review);
        return reviews;
    }

    @Transactional
    public String updateReview(Long id, UpdateReviewForm form) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurant with id " + id + " not found"));
        List<String> reviews = restaurant.getReviews();
        reviews.replaceAll(r -> r.equals(form.getReview()) ? form.getUpdatedReview() : r);
        String savedReview = repository.save(restaurant).getReviews()
                .stream()
                .filter(r -> r.equals(form.getUpdatedReview()))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        return savedReview;
    }

    @Transactional
    public void deleteReview(Long id, String review) {
        Restaurant restaurant = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurant with id " + id + " not found"));
        if (restaurant.getReviews()
                .stream()
                .anyMatch(r -> r.equals(review))) {
            restaurant.getReviews().remove(review);
            repository.save(restaurant);
        }
    }

}
