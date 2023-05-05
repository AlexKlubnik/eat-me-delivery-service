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
import org.mockito.InOrder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DishServiceTest {

    private DishRepository repository;

    private RestaurantRepository restaurantRepository;

    private DishConverter converter;

    private DishService service;

    private Method checkAndChangeDish;

    private Dish dish1;
    private Dish dish2;
    private DishDto dishDto1;
    private DishDto dishDto2;
    private Restaurant restaurant;


    @BeforeEach
    void setUp() throws Exception {
        converter = new DishConverter();
        repository = mock(DishRepository.class);
        restaurantRepository = mock(RestaurantRepository.class);
        service = spy(new DishService(repository, converter, restaurantRepository));

        initRestaurant();
        initDish1();
        initDish2();
        initDishDto1();
        initDishDto2();
        checkAndChangeDish = getCheckAndCheckDishMethod();
    }

    private void initDishDto2() {
        dishDto2 = new DishDto();
        dishDto2.setName("Hamburger");
        dishDto2.setDescription("With cow steak");
        dishDto2.setPrice(21.08);
    }

    private void initDishDto1() {
        dishDto1 = new DishDto();
        dishDto1.setName("Hot dog");
        dishDto1.setDescription("With french dog");
        dishDto1.setPrice(15.99);
    }

    private void initDish2() {
        dish2 = new Dish();
        dish2.setId(2L);
        dish2.setName("Hamburger");
        dish2.setRestaurant(restaurant);
        dish2.setDescription("With cow steak");
        dish2.setPrice(21.08);
    }

    private void initDish1() {
        dish1 = new Dish();
        dish1.setId(1L);
        dish1.setName("Hot dog");
        dish1.setRestaurant(restaurant);
        dish1.setDescription("With french dog");
        dish1.setPrice(15.99);
    }

    private void initRestaurant() {
        restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("My restaurant");
        restaurant.setDescription("Comfortable restaurant");
        restaurant.setAddress(new Address());
        restaurant.setDishes(new ArrayList<>());
        restaurant.setReviews(new ArrayList<>());
    }

    private Method getCheckAndCheckDishMethod() throws Exception {
        Method checkAndChangeDish = DishService.class
                .getDeclaredMethod("checkAndChangeDish", DishDto.class, Dish.class);
        checkAndChangeDish.setAccessible(true);
        return checkAndChangeDish;
    }

    @Test
    void findAllByRestaurantId() {
        when(repository.findAllByRestaurantId(anyLong())).thenReturn(Arrays.asList(dish1, dish2));
        List<DishListView> expectedList = service.findAllByRestaurantId(1L);
        verify(repository).findAllByRestaurantId(anyLong());
        verifyNoMoreInteractions(repository);
        assertEquals(expectedList, converter.convertToListView(Arrays.asList(dish1, dish2)));
    }

    @Test
    void findByRestaurantIdAndId_WhenDishExist() {
        when(repository.findByRestaurantIdAndId(1L, 1L)).thenReturn(Optional.of(dish1));
        DishDto expected = service.findByRestaurantIdAndId(1L, 1L);
        verify(repository, times(1)).findByRestaurantIdAndId(1L, 1L);
        verifyNoMoreInteractions(repository);
        assertEquals(expected, converter.convert(dish1));
    }

    @Test
    void findByRestaurantIdAndId_WhenDishNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> service.findByRestaurantIdAndId(500L, 500L));
    }

    @Test
    void save_WhenRestaurantExist() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));
        when(repository.save(any())).thenReturn(dish1);
        InOrder inOrder = inOrder(restaurantRepository, repository);
        DishListView expected = service.save(1L, dishDto1);

        verify(restaurantRepository, times(1)).findById(1L);
        verify(repository).save(any());
        verifyNoMoreInteractions(repository);
        inOrder.verify(restaurantRepository).findById(1L);
        inOrder.verify(repository).save(any());
        assertEquals(expected, converter.convertToListView(dish1));
    }

    @Test
    void save_WhenRestaurantNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> service.save(500L, dishDto1));
    }

    @Test
    void saveUpdatedDish_WhenDishExist_AndRestaurantIdMatch() {
        when(repository.findById(1L)).thenReturn(Optional.of(dish1));
        when(repository.save(any())).thenReturn(dish2);
        InOrder inOrder = inOrder(repository);

        DishDto expected = service.save(1L, 1L, dishDto2);

        verify(repository).findById(1L);
        verify(repository, times(1)).save(any());
        verifyNoMoreInteractions(repository);
        inOrder.verify(repository).findById(1L);
        inOrder.verify(repository).save(any());
        assertEquals(expected, converter.convert(dish2));
    }

    @Test
    void saveUpdatedDish_WhenDishExist_AndRestaurantIdNotMatch() {
        assertThrows(ResourceNotFoundException.class, () -> service.save(500L, 1L, dishDto2));
    }

    @Test
    void saveUpdatedDish_WhenDishNotExists() {
        assertThrows(ResourceNotFoundException.class, () -> service.save(1L, 500L, dishDto2));
    }

    @Test
    void checkAndChangeDish_WithDifferentNames() throws Exception {
        checkAndChangeDish.invoke(service, dishDto2, dish1);
        assertEquals(dishDto2.getName(), dish1.getName());
    }

    @Test
    void checkAndChangeDish_WithSameNames() throws Exception {
        dishDto2.setName(dish1.getName());
        checkAndChangeDish.invoke(service, dishDto2, dish1);
        assertEquals(dishDto2.getName(), dish1.getName());
    }

    @Test
    void checkAndChangeDish_WithDifferentPrices() throws Exception {
        checkAndChangeDish.invoke(service, dishDto2, dish1);
        assertEquals(dishDto2.getPrice(), dish1.getPrice());
    }

    @Test
    void checkAndChangeDish_WithSamePrices() throws Exception {
        dishDto2.setPrice(dish1.getPrice());
        checkAndChangeDish.invoke(service, dishDto2, dish1);
        assertEquals(dishDto2.getPrice(), dish1.getPrice());
    }

    @Test
    void checkAndChangeDish_WithDifferentDescriptions() throws Exception {
        checkAndChangeDish.invoke(service, dishDto2, dish1);
        assertEquals(dishDto2.getDescription(), dish1.getDescription());
    }

    @Test
    void checkAndChangeDish_WithSameDescriptions() throws Exception {
        dishDto2.setDescription(dish1.getDescription());
        checkAndChangeDish.invoke(service, dishDto2, dish1);
        assertEquals(dishDto2.getDescription(), dish1.getDescription());
    }

    @Test
    void deleteByRestaurantIdAndId_WhenDishExist_AndRestaurantIdMatch() {
        when(repository.findById(1L)).thenReturn(Optional.of(dish1));
        InOrder inOrder = inOrder(repository);

        service.deleteByRestaurantIdAndId(1L, 1L);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(repository);
        inOrder.verify(repository).findById(1L);
        inOrder.verify(repository).deleteById(1L);
    }

    @Test
    void deleteByRestaurantIdAndId_WhenDishExist_AndRestaurantIdNotMatch() {
        when(repository.findById(1L)).thenReturn(Optional.of(dish1));

        service.deleteByRestaurantIdAndId(500L, 1L);
        verify(repository, times(1)).findById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deleteByRestaurantIdAndId_WhenDishNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> service.deleteByRestaurantIdAndId(1L, 500L));
    }


}