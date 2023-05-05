package by.klubnikov.eatmedelivery.repository;

import by.klubnikov.eatmedelivery.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {

    List<Dish> findAllByRestaurantId(Long id);

   Optional<Dish> findByRestaurantIdAndId(Long restaurantId, Long id);
}
