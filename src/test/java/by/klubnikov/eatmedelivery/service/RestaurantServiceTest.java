package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.AddressConverter;
import by.klubnikov.eatmedelivery.converter.DishConverter;
import by.klubnikov.eatmedelivery.converter.RestaurantConverter;
import by.klubnikov.eatmedelivery.dto.AddressDto;
import by.klubnikov.eatmedelivery.dto.RestaurantListView;
import by.klubnikov.eatmedelivery.dto.RestaurantPageView;
import by.klubnikov.eatmedelivery.dto.UpdateReviewForm;
import by.klubnikov.eatmedelivery.entity.Address;
import by.klubnikov.eatmedelivery.entity.Restaurant;
import by.klubnikov.eatmedelivery.error.ResourceNotFoundException;
import by.klubnikov.eatmedelivery.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {

    private RestaurantRepository repository;
    private AddressService addressService;
    private RestaurantConverter converter;
    private AddressConverter addressConverter;
    private DishConverter dishConverter;

    private Method checkAndChangeRestaurant;
    private RestaurantService service;
    private Address address1;
    private Address address2;
    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private UpdateReviewForm updateReviewForm;

    @BeforeEach
    void setUp() throws Exception {
        repository = mock(RestaurantRepository.class);
        addressService = mock(AddressService.class);
        addressConverter = new AddressConverter();
        converter = new RestaurantConverter(dishConverter, addressConverter);
        dishConverter = new DishConverter();
        service = spy(new RestaurantService(repository, converter, addressService));
        checkAndChangeRestaurant = getCheckAndChangeRestaurantMethod();

        initAddress1();
        initAddress2();
        initRestaurant1();
        initRestaurant2();
        initUpdateReviewForm();
    }

    private void initUpdateReviewForm() {
        updateReviewForm = new UpdateReviewForm();
        updateReviewForm.setReview(restaurant1.getReviews().get(0));
        updateReviewForm.setUpdatedReview("Updated review");
    }

    private void initRestaurant2() {
        restaurant2 = new Restaurant();
        restaurant2.setId(2L);
        restaurant2.setName("My second restaurant");
        restaurant2.setDescription("Family restaurant");
        restaurant2.setAddress(address2);
        restaurant2.setDishes(new ArrayList<>());
        restaurant2.setReviews(new ArrayList<>());
    }

    private void initRestaurant1() {
        restaurant1 = new Restaurant();
        restaurant1.setId(1L);
        restaurant1.setName("My restaurant");
        restaurant1.setDescription("Comfortable restaurant");
        restaurant1.setAddress(address1);
        restaurant1.setDishes(new ArrayList<>());
        restaurant1.setReviews(new ArrayList<>());
        restaurant1.getReviews().add("Nice restaurant");
        restaurant1.getReviews().add("Bad restaurant");
    }

    private void initAddress2() {
        address2 = new Address();
        address2.setId(1L);
        address2.setCity("Another city");
        address2.setStreet("Another street");
        address2.setHouseNumber(100);
        address2.setBuildingNumber(1);
        address2.setApartmentNumber(377);
    }

    private void initAddress1() {
        address1 = new Address();
        address1.setId(1L);
        address1.setCity("City");
        address1.setStreet("Street");
        address1.setHouseNumber(100);
        address1.setBuildingNumber(1);
        address1.setApartmentNumber(377);
    }

    private Method getCheckAndChangeRestaurantMethod() throws Exception {
        Method checkAndChangeRestaurant = RestaurantService.class
                .getDeclaredMethod("checkAndChangeRestaurant", RestaurantPageView.class, Restaurant.class);
        checkAndChangeRestaurant.setAccessible(true);
        return checkAndChangeRestaurant;
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(Arrays.asList(restaurant1, restaurant2));
        List<RestaurantListView> expected = service.findAll();
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
        assertEquals(expected, converter.convertToListView(Arrays.asList(restaurant1, restaurant2)));
    }

    @Test
    void findById_WhenRestaurantExist() {
        when(repository.findById(1L)).thenReturn(Optional.of(restaurant1));
        RestaurantPageView expected = service.findById(1L);
        verify(repository, times(1)).findById(1L);
        verifyNoMoreInteractions(repository);
        assertEquals(expected, converter.convertToPageView(restaurant1));
    }

    @Test
    void findById_WhenRestaurantNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> service.findById(500L));
    }

    @Test
    void save() {
        when(repository.save(any())).thenReturn(restaurant1);
        RestaurantListView expected = service.save(converter.convertToPageView(restaurant1));
        verify(repository, times(1)).save(any());
        verifyNoMoreInteractions(repository);
        assertEquals(expected, converter.convertToListView(restaurant1));
    }

    @Test
    void saveUpdatedRestaurant_WhenRestaurantExists() {
        RestaurantPageView updatedRestaurant = converter.convertToPageView(restaurant2);
        restaurant2.setId(1L);
        Restaurant savedRestaurant = restaurant2;
        AddressDto updatedAddress = addressConverter.convert(address2);

        when(repository.findById(1L)).thenReturn(Optional.of(restaurant1));
        when(repository.save(any())).thenReturn(savedRestaurant);
        InOrder inOrder = inOrder(repository, addressService);
        RestaurantPageView expected = service.save(1L, updatedRestaurant);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any());
        verify(addressService, times(1)).save(1L, updatedAddress);
        verifyNoMoreInteractions(repository, addressService);

        inOrder.verify(repository).findById(1L);
        inOrder.verify(addressService).save(1L, updatedAddress);
        inOrder.verify(repository).save(any());
        inOrder.verifyNoMoreInteractions();

        assertEquals(expected.getName(), updatedRestaurant.getName());
        assertEquals(expected.getDescription(), updatedRestaurant.getDescription());
        assertEquals(expected.getAddress(), updatedRestaurant.getAddress());
    }

    @Test
    void saveUpdatedRestaurant_WhenRestaurantNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> service.save(500L, new RestaurantPageView()));
    }

    @Test
    void checkAndChangeRestaurant_WithSameNames() throws Exception {
        RestaurantPageView updatedRestaurant = converter.convertToPageView(restaurant2);
        Restaurant restaurantFromDb = restaurant1;
        updatedRestaurant.setName(restaurantFromDb.getName());
        checkAndChangeRestaurant.invoke(service, updatedRestaurant, restaurantFromDb);
        assertEquals(restaurantFromDb.getName(), updatedRestaurant.getName());
    }

    @Test
    void checkAndChangeRestaurant_WithDifferentNames() throws Exception {
        RestaurantPageView updatedRestaurant = converter.convertToPageView(restaurant2);
        Restaurant restaurantFromDb = restaurant1;
        checkAndChangeRestaurant.invoke(service, updatedRestaurant, restaurantFromDb);
        assertEquals(restaurantFromDb.getName(), updatedRestaurant.getName());
    }

    @Test
    void checkAndChangeRestaurant_WithSameDescriptions() throws Exception {
        RestaurantPageView updatedRestaurant = converter.convertToPageView(restaurant2);
        Restaurant restaurantFromDb = restaurant1;
        updatedRestaurant.setDescription(restaurantFromDb.getDescription());
        checkAndChangeRestaurant.invoke(service, updatedRestaurant, restaurantFromDb);
        assertEquals(restaurantFromDb.getDescription(), updatedRestaurant.getDescription());
    }

    @Test
    void checkAndChangeRestaurant_WithDifferentDescriptions() throws Exception {
        RestaurantPageView updatedRestaurant = converter.convertToPageView(restaurant2);
        Restaurant restaurantFromDb = restaurant1;
        checkAndChangeRestaurant.invoke(service, updatedRestaurant, restaurantFromDb);
        assertEquals(restaurantFromDb.getDescription(), updatedRestaurant.getDescription());
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(repository, times(1)).deleteById(1L);
        verifyNoMoreInteractions(repository);
    }


    @Test
    void findAllReviews_WhenRestaurantExist() {
        when(repository.findById(1L)).thenReturn(Optional.of(restaurant1));
        List<String> expected = service.findAllReviews(1L);
        verify(repository, times(1)).findById(1L);
        verifyNoMoreInteractions(repository);
        assertEquals(expected, List.of("Nice restaurant", "Bad restaurant"));
    }

    @Test
    void findAllReviews_WhenRestaurantNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> service.findAllReviews(500L));
    }

    @Test
    void saveReview() {
        when(repository.findById(1L)).thenReturn(Optional.of(restaurant1));
        List<String> expected = service.saveReview(1L, "New review");
        verify(repository, times(1)).findById(1L);
        verifyNoMoreInteractions(repository);
        assertEquals(expected.size(), 3);
        assertTrue(expected
                .stream()
                .anyMatch(review -> review.equals("New review")));
    }

    @Test
    void saveReview_WhenRestaurantNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> service.saveReview(500L, "New review"));
    }

    @Test
    void updateReview_WhenRestaurantExist() {
        when(repository.findById(1L)).thenReturn(Optional.of(restaurant1));
        when(repository.save(any())).thenReturn(new Restaurant());
        service.updateReview(1L, updateReviewForm);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any());
        verifyNoMoreInteractions(repository);
    }

    @Test
    void updateReview_WhenRestaurantNotExist() {
        assertThrows(ResourceNotFoundException.class, () -> service.updateReview(500L, updateReviewForm));
    }

    @Test
    void deleteReview_WhenRestaurantExist_AndReviewExist() {
        String removableReview = restaurant1.getReviews().get(0);
        when(repository.findById(1L)).thenReturn(Optional.of(restaurant1));
        service.deleteReview(1L, removableReview);
        verify(repository, times(1)).findById(1L);
        verify(repository).save(any(Restaurant.class));
        verifyNoMoreInteractions(repository);

        assertEquals(restaurant1.getReviews().size(), 1);
        assertTrue(restaurant1.getReviews()
                .stream()
                .noneMatch(review -> review.equals(removableReview)));
    }

    @Test
    void deleteReview_WhenRestaurantExist_AndReviewNotExist() {
        String removableReview = "Not existing review";
        when(repository.findById(1L)).thenReturn(Optional.of(restaurant1));
        service.deleteReview(1L, removableReview);
        verify(repository, times(1)).findById(1L);
        verifyNoMoreInteractions(repository);

        assertEquals(restaurant1.getReviews().size(), 2);
    }

    @Test
    void deleteReview_WhenRestaurantNotExist() {
        String removableReview = restaurant1.getReviews().get(0);
        assertThrows(ResourceNotFoundException.class, () -> service.deleteReview(500L, removableReview));
    }
}