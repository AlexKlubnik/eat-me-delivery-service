package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.DishConverter;
import by.klubnikov.eatmedelivery.entity.Dish;
import by.klubnikov.eatmedelivery.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class DishService {

    private final DishRepository dishRepository;

    private final DishConverter dishConverter;

    public List<Dish> findDishesByRestaurantId(Long restaurantId) {
        return dishRepository.findAllByRestaurantId(restaurantId);
    }

}
