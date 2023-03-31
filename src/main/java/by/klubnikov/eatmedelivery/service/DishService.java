package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.DishConverter;
import by.klubnikov.eatmedelivery.dto.DishDto;
import by.klubnikov.eatmedelivery.dto.DishListView;
import by.klubnikov.eatmedelivery.entity.Dish;
import by.klubnikov.eatmedelivery.entity.Restaurant;
import by.klubnikov.eatmedelivery.error.ResourceNotFoundException;
import by.klubnikov.eatmedelivery.repository.DishRepository;
import by.klubnikov.eatmedelivery.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor

public class DishService {

    private final DishRepository repository;
    private final DishConverter converter;
    private final RestaurantRepository restaurantRepository;


    public List<DishListView> findAllByRestaurantId(Long restaurantId) {
        return converter.convertToListView(repository
                .findAllByRestaurantId(restaurantId));
    }

    public DishDto findByRestaurantIdAndId(Long restaurantId, Long id) {
        Dish dish = repository.findByRestaurantIdAndId(restaurantId, id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurant with id " + restaurantId + "  or dish with id " + id + " not found"));
        return converter.convert(dish);
    }

    public DishListView save(Long restaurantId, DishDto dish) {
        Restaurant restaurantFromDb = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Restaurant with id " + restaurantId + " not found"));
        Dish dishToSave = converter.convert(dish);
        dishToSave.setRestaurant(restaurantFromDb);
        Dish savedDish = repository.save(dishToSave);
        return converter.convertToListView(savedDish);
    }

    public DishDto save(Long restaurantId, Long id, DishDto dish) {
        Dish dishFromDb = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Dish with id " + id + " not found"));
        Long restaurantIdFromDb = dishFromDb.getRestaurant().getId();
        if (Objects.equals(restaurantId, restaurantIdFromDb)) {
            checkAndChangeDish(dish, dishFromDb);
        }
        Dish savedDish = repository.save(dishFromDb);
        return converter.convert(savedDish);
    }

    private static void checkAndChangeDish(DishDto dish, Dish dishFromDb) {
        if (!dish.getName().equals(dishFromDb.getName()))
            dishFromDb.setName(dish.getName());
        if (dish.getPrice() != dishFromDb.getPrice())
            dishFromDb.setPrice(dish.getPrice());
        if (!dish.getDescription().equals(dishFromDb.getDescription()))
            dishFromDb.setDescription(dish.getDescription());
    }

    public void deleteByRestaurantIdAndId(Long restaurantId, Long id) {
        Long checkableId = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Dish with id " + id + " not found"))
                .getRestaurant()
                .getId();
        if (Objects.equals(checkableId, restaurantId)) {
            repository.deleteById(id);
        }
    }

}
