package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.AddressConverter;
import by.klubnikov.eatmedelivery.converter.DishConverter;
import by.klubnikov.eatmedelivery.converter.RestaurantConverter;
import by.klubnikov.eatmedelivery.dto.AddressDto;
import by.klubnikov.eatmedelivery.dto.RestaurantListView;
import by.klubnikov.eatmedelivery.dto.RestaurantPageView;
import by.klubnikov.eatmedelivery.entity.Address;
import by.klubnikov.eatmedelivery.entity.Restaurant;
import by.klubnikov.eatmedelivery.error.ResourceNotFoundException;
import by.klubnikov.eatmedelivery.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantServiceTest {

    @Mock
    private RestaurantRepository repository;

    @Mock
    private AddressService addressService;
    private RestaurantConverter converter;
    private AddressConverter addressConverter;
    @Spy
    private RestaurantService service;
    private Address address1;
    private Address address2;
    private Restaurant restaurant1;
    private Restaurant restaurant2;

    @BeforeEach
    void setUp() {
        DishConverter dishConverter = new DishConverter();

        repository = Mockito.mock(RestaurantRepository.class);
        addressService = Mockito.mock(AddressService.class);
        addressConverter = new AddressConverter();
        converter = new RestaurantConverter(dishConverter, addressConverter);
        service = Mockito.spy(new RestaurantService(repository, converter, addressService));

        address1 = new Address();
        address1.setId(1L);
        address1.setCity("City");
        address1.setStreet("Street");
        address1.setHouseNumber(100);
        address1.setBuildingNumber(1);
        address1.setApartmentNumber(377);

        address2 = new Address();
        address2.setId(1L);
        address2.setCity("Another city");
        address2.setStreet("Another street");
        address2.setHouseNumber(100);
        address2.setBuildingNumber(1);
        address2.setApartmentNumber(377);

        restaurant1 = new Restaurant();
        restaurant1.setId(1L);
        restaurant1.setName("My restaurant");
        restaurant1.setDescription("Comfortable restaurant");
        restaurant1.setAddress(address1);
        restaurant1.setDishes(new ArrayList<>());
        restaurant1.setReviews(new ArrayList<>());

        restaurant2 = new Restaurant();
        restaurant2.setId(2L);
        restaurant2.setName("My second restaurant");
        restaurant2.setDescription("Family restaurant");
        restaurant2.setAddress(address2);
        restaurant2.setDishes(new ArrayList<>());
        restaurant2.setReviews(new ArrayList<>());

    }

    @Test
    void findAll() {
        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(restaurant1, restaurant2));
        List<RestaurantListView> expected = service.findAll();
        Mockito.verify(repository, Mockito.times(1)).findAll();
        assertEquals(expected, converter.convertToListView(Arrays.asList(restaurant1, restaurant2)));
    }

    @Test
    void findById() {
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(restaurant1));
        RestaurantPageView expected = service.findById(1L);
        Mockito.verify(repository, Mockito.times(1)).findById(1L);
        assertThrows(ResourceNotFoundException.class, () -> service.findById(500L));
        assertEquals(expected, converter.convertToPageView(restaurant1));
    }


    @Test
    void save() {
        Mockito.when(repository.save(Mockito.any())).thenReturn(restaurant1);
        RestaurantListView expected = service.save(converter.convertToPageView(restaurant1));
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
        assertEquals(expected, converter.convertToListView(restaurant1));
    }

    @Test
    void saveUpdatedRestaurant() {
        RestaurantPageView updatedRestaurant = converter.convertToPageView(restaurant2);
        restaurant2.setId(1L);
        Restaurant savedRestaurant = restaurant2;
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(restaurant1));
        Mockito.when(repository.save(Mockito.any())).thenReturn(savedRestaurant);
        RestaurantPageView expected = service.save(1L, updatedRestaurant);

        Mockito.verify(repository, Mockito.times(1)).findById(1L);
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
        Mockito.verify(addressService, Mockito.times(1)).save(1L, addressConverter.convert(address2));
        assertThrows(ResourceNotFoundException.class, () -> service.save(500L, updatedRestaurant));
        assertEquals(expected.getName(), updatedRestaurant.getName());
        assertEquals(expected.getDescription(), updatedRestaurant.getDescription());
        assertEquals(expected.getAddress(), updatedRestaurant.getAddress());

    }

    @Test
    void deleteById() {
    }

    @Test
    void findAllReviews() {
    }

    @Test
    void saveReview() {
    }

    @Test
    void updateReview() {
    }

    @Test
    void deleteReview() {
    }
}