package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.DishConverter;
import by.klubnikov.eatmedelivery.dto.DishDto;
import by.klubnikov.eatmedelivery.entity.Dish;
import by.klubnikov.eatmedelivery.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class DishService {

    private final DishRepository repository;
    private final DishConverter converter;

    public List<DishDto> findAllByRestaurantId(Long restaurantId) {
        return converter.convertToDto(repository
                .findAllByRestaurantId(restaurantId));
    }

    public DishDto update(Long id, DishDto dish) {
        Dish dishFromDb = repository.findById(id).orElseThrow();
        if (!dish.getName().isBlank())
            dishFromDb.setName(dish.getName());
        if (dish.getPrice() != 0)
            dishFromDb.setPrice(dish.getPrice());
        if (!dish.getDescription().isBlank())
            dishFromDb.setDescription(dish.getDescription());
        repository.save(dishFromDb);
        return converter.convertToDto(dishFromDb);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }


}
