package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.DishConverter;
import by.klubnikov.eatmedelivery.dto.DishDto;
import by.klubnikov.eatmedelivery.dto.DishListView;
import by.klubnikov.eatmedelivery.entity.Address;
import by.klubnikov.eatmedelivery.entity.Dish;
import by.klubnikov.eatmedelivery.entity.Restaurant;
import by.klubnikov.eatmedelivery.error.ResourceNotFoundException;
import by.klubnikov.eatmedelivery.repository.DishRepository;
import by.klubnikov.eatmedelivery.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DishServiceTest {

    @Mock
    private DishRepository repository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Spy
    private DishService service;

    private Dish dish1;
    private Dish dish2;
    private DishConverter converter;
    private DishDto dishDto1;
    private DishDto dishDto2;
    private Restaurant restaurant;


    @BeforeEach
    void setUp() {
        converter = new DishConverter();
        repository = Mockito.mock(DishRepository.class);
        restaurantRepository = Mockito.mock(RestaurantRepository.class);
        service = Mockito.spy(new DishService(repository, converter, restaurantRepository));

        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("My restaurant");
        restaurant.setDescription("Comfortable restaurant");
        restaurant.setAddress(new Address());
        restaurant.setDishes(new ArrayList<>());
        restaurant.setReviews(new ArrayList<>());

        dish1 = new Dish();
        dish1.setId(1L);
        dish1.setName("Hot dog");
        dish1.setRestaurant(restaurant);
        dish1.setDescription("With french dog");
        dish1.setPrice(15.99);

        dish2 = new Dish();
        dish2.setId(2L);
        dish2.setName("Hamburger");
        dish2.setRestaurant(restaurant);
        dish2.setDescription("With cow steak");
        dish2.setPrice(21.08);

        dishDto1 = new DishDto();
        dishDto1.setName("Hot dog");
        dishDto1.setDescription("With french dog");
        dishDto1.setPrice(15.99);

        dishDto2 = new DishDto();
        dishDto2.setName("Pizza");
        dishDto2.setDescription("Italian classic food");
        dishDto2.setPrice(20.1);

    }

    @Test
    void findAllByRestaurantId() {
        Mockito.when(repository.findAllByRestaurantId(Mockito.anyLong())).thenReturn(Arrays.asList(dish1, dish2));

        List<DishListView> expectedList = service.findAllByRestaurantId(1L);
        Mockito.verify(repository, Mockito.times(1)).findAllByRestaurantId(1L);
        assertEquals(expectedList, converter.convertToListView(Arrays.asList(dish1, dish2)));

    }

    @Test
    void findByRestaurantIdAndId() {
        Mockito.when(repository.findByRestaurantIdAndId(1L, 1L)).thenReturn(Optional.of(dish1));
        DishDto expected = service.findByRestaurantIdAndId(1L, 1L);

        Mockito.verify(repository, Mockito.times(1)).findByRestaurantIdAndId(1L, 1L);
        assertThrows(ResourceNotFoundException.class, () -> service.findByRestaurantIdAndId(500L, 500L));
        assertEquals(expected, converter.convert(dish1));
    }

    @Test
    void save() {
        Mockito.when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        DishListView expected = service.save(1L, dishDto1);

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
        assertThrows(ResourceNotFoundException.class, () -> service.save(500L, dishDto1));
        assertEquals(expected.getName(), dishDto1.getName());
        assertEquals(expected.getPrice(), dishDto1.getPrice());
    }

    @Test
    void saveUpdatedDish() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(dish1));
        DishDto expected = service.save(1L, 1L, dishDto2);

        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
        assertThrows(ResourceNotFoundException.class, () -> service.save(1L, 500L, dishDto2));

        Dish updatedDish = converter.convert(dishDto2);
        updatedDish.setId(dish1.getId());
        updatedDish.setRestaurant(dish1.getRestaurant());

        assertEquals(expected, converter.convert(updatedDish));

    }

    @Test
    void deleteByRestaurantIdAndId() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(dish1));
        service.deleteByRestaurantIdAndId(1L, 1L);
        Mockito.verify(repository, Mockito.times(1)).deleteById(1L);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteByRestaurantIdAndId(1L, 500L));
    }

}