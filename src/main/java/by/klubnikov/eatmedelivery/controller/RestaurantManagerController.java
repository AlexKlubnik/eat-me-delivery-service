package by.klubnikov.eatmedelivery.controller;

import by.klubnikov.eatmedelivery.dto.*;
import by.klubnikov.eatmedelivery.service.DishService;
import by.klubnikov.eatmedelivery.service.RestaurantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("restaurants-manager")
@SecurityRequirement(name = "JWT")
@Slf4j
@Tag(name = "Restaurants manager controller",
        description = "This controller allows to watch, support and maintain restaurants and dishes from service.")
public class RestaurantManagerController {

    private final RestaurantService restaurantService;

    private final DishService dishService;

    @Operation(summary = "Find all restaurants",
            description = "This method returns all restaurants, stored in database",
            responses = @ApiResponse(responseCode = "200",
                    description = "Returns list RestaurantListView Dtos",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RestaurantListView.class)),
                            examples = @ExampleObject(value =
                                    """
                                            [
                                              {
                                                "name": "Dodo",
                                                "address": {
                                                  "city": "Minsk",
                                                  "street": "Selivanova",
                                                  "houseNumber": 33,
                                                  "buildingNumber": 0,
                                                  "apartmentNumber": 0
                                                }
                                              },
                                              {
                                                "name": "India food",
                                                "address": {
                                                  "city": "Minsk",
                                                  "street": "Korzha",
                                                  "houseNumber": 26,
                                                  "buildingNumber": 0,
                                                  "apartmentNumber": 0
                                                }
                                              }
                                            ]"""))}))
    @GetMapping
    public List<RestaurantListView> findAll() {
        return restaurantService.findAll();
    }

    @Operation(summary = "Find restaurant",
            description = "This method returns one restaurant from database if present",
            responses = {@ApiResponse(responseCode = "200",
                    description = "Returns RestaurantPageView Dto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantPageView.class),
                            examples = @ExampleObject(value =
                                    """
                                            {
                                              "name": "India food",
                                              "address": {
                                                "city": "Minsk",
                                                "street": "Korzha",
                                                "houseNumber": 26,
                                                "buildingNumber": 0,
                                                "apartmentNumber": 0
                                              },
                                              "description": "very spicy dishes"
                                            }"""
                            ))}),
                    @ApiResponse(responseCode = "404",
                            description = "Restaurant with id \"X\" not found",
                            content = @Content)})

    @GetMapping("{id}")
    public RestaurantPageView findById(
            @Parameter(description = "Restaurant's id from database", required = true)
            @PathVariable Long id) {
        return restaurantService.findById(id);
    }

    @Operation(summary = "Create restaurant",
            description = "Takes parameters from registration form, creates new restaurant and stores it to database",
            responses = {@ApiResponse(responseCode = "201", description = "Returns RestaurantListView dto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantListView.class),
                            examples = @ExampleObject(value =
                                    """
                                            {
                                                "name": "India food",
                                                "address": {
                                                  "city": "Minsk",
                                                  "street": "Korzha",
                                                  "houseNumber": 26,
                                                  "buildingNumber": 0,
                                                  "apartmentNumber": 0
                                                }
                                              }"""))})})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantListView save(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Retrieved from form restaurant",
                    required = true,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantPageView.class),
                            examples = @ExampleObject(value =
                                    """
                                            {
                                              "name": "India food",
                                              "address": {
                                                "city": "Minsk",
                                                "street": "Korzha",
                                                "houseNumber": 26,
                                                "buildingNumber": 0,
                                                "apartmentNumber": 0
                                              },
                                              "description": "very spicy dishes"
                                            }"""
                            ))})
           @RequestBody RestaurantPageView restaurant) {
        return restaurantService.save(restaurant);
    }

    @Operation(summary = "Update restaurant",
            description = "This method updates specific restaurant",
            responses = {@ApiResponse(responseCode = "200",
                    description = "Returns RestaurantPageView Dto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantPageView.class),
                            examples = @ExampleObject(value =
                                    """
                                            {
                                              "name": "Updated restaurant",
                                              "address": {
                                                "city": "Updated city",
                                                "street": "Updated street",
                                                "houseNumber": 115,
                                                "buildingNumber": 0,
                                                "apartmentNumber": 0
                                              },
                                              "description": "Updated description"
                                            }"""
                            ))}),
                    @ApiResponse(responseCode = "404",
                            description = "Restaurant with id \"X\" not found",
                            content = @Content)})


    @PutMapping("{id}")
    public RestaurantPageView update(
            @Parameter(description = "Restaurant's id from database", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Retrieved from form restaurant",
                    required = true,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RestaurantPageView.class),
                            examples = @ExampleObject(value =
                                    """
                                            {
                                              "name": "Updatable restaurant",
                                              "address": {
                                                "city": "Updated city",
                                                "street": "Updated street",
                                                "houseNumber": 115,
                                                "buildingNumber": 0,
                                                "apartmentNumber": 0
                                              },
                                              "description": "Updatable description"
                                            }"""
                            ))})
            @RequestBody RestaurantPageView restaurant) {
        return restaurantService.save(id, restaurant);
    }

    @Operation(summary = "Delete restaurant",
            description = "Allows to delete specific restaurant",
            responses = {@ApiResponse(responseCode = "204", description = "Returns no content",
                    content = @Content),
                    @ApiResponse(responseCode = "404",
                            description = "Restaurant with id \"X\" not found",
                            content = @Content)})
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(
            @Parameter(description = "Restaurant's id from database", required = true)
            @PathVariable Long id) {
        restaurantService.deleteById(id);
    }

    @Operation(summary = "Find dishes of specific restaurant",
            description = "Returns list of dishes of one specific restaurant",
            responses = {@ApiResponse(responseCode = "200",
                    description = "Returns list of DishListView Dtos",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DishListView.class)),
                            examples = @ExampleObject(value =
                                    """
                                            [
                                              {
                                                "name": "Pizza",
                                                "price": 18.99
                                              },
                                              {
                                                "name": "Sushi",
                                                "price": 15
                                              }
                                            ]"""
                            ))}),
                    @ApiResponse(responseCode = "404",
                            description = "Restaurant with id \"X\" not found",
                            content = @Content)})

    @GetMapping("{id}/dishes")
    public List<DishListView> findAllDishes(
            @Parameter(description = "Restaurant's id from database", required = true)
            @PathVariable Long id) {
        return dishService.findAllByRestaurantId(id);
    }

    @Operation(summary = "Create dish",
            description = "Allows to create new dish of specific restaurant",
            responses = {@ApiResponse(responseCode = "201", description = "Returns DishListView dto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DishListView.class),
                            examples = @ExampleObject(value =
                                    """
                                            {
                                              "name": "Karri",
                                              "price": 15.65
                                            }"""
                            ))}),
                    @ApiResponse(responseCode = "404",
                            description = "Restaurant with id \"X\" not found",
                            content = @Content)})

    @PostMapping("{id}/dishes")
    @ResponseStatus(HttpStatus.CREATED)
    public DishListView saveDish(
            @Parameter(description = "Restaurant's id from database", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Retrieved from form dish",
                    required = true,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DishDto.class),
                            examples = @ExampleObject(value =
                                    """
                                            {
                                              "name": "Karri",
                                              "description": "Hot and spicy",
                                              "price": 15.65
                                            }"""
                            ))})
            @RequestBody DishDto dishDto) {
        return dishService.save(id, dishDto);
    }

    @Operation(summary = "Finds dish",
            description = "Returns specific dish of specific restaurant",
            responses = {@ApiResponse(responseCode = "200",
                    description = "Returns DishDto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DishDto.class),
                            examples = @ExampleObject(value =
                                    """
                                            {
                                              "name": "Sushi",
                                              "description": "Very tasty",
                                              "price": 15,
                                              "restaurantName": "Dodo"
                                            }"""
                            ))}),
                    @ApiResponse(responseCode = "404",
                            description = "Restaurant with id \"X\"  or dish with id \"Y\" not found",
                            content = @Content)})

    @GetMapping("{id}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.OK)
    public DishDto findByRestaurantIdAndDishId(
            @Parameter(description = "Restaurant's id from database", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dishes id from database", required = true)
            @PathVariable Long dishId) {
        return dishService.findByRestaurantIdAndId(id, dishId);
    }

    @Operation(summary = "Update dish method",
            description = "Allows to update specific dish of the restaurant",
            responses = {@ApiResponse(responseCode = "200", description = "Returns dishDto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DishDto.class),
                            examples = @ExampleObject(value =
                                    """
                                            {
                                              "name": "Updatable dish",
                                              "description": "Fat and tasty",
                                              "price": 13.99,
                                              "restaurantName": "And doners for all"
                                            }"""
                            ))}),
                    @ApiResponse(responseCode = "404",
                            description = "Restaurant with id \"X\"  or dish with id \"Y\" not found",
                            content = @Content)})

    @PutMapping("{id}/dishes/{dishId}")
    public DishDto updateDish(
            @Parameter(description = "Restaurant's id from database", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dishes id from database", required = true)
            @PathVariable Long dishId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Retrieved from form dish",
                    required = true,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DishDto.class),
                            examples = @ExampleObject(value =
                                    """
                                            {
                                              "name": "Updated dish",
                                              "description": "Fat and tasty",
                                              "price": 13.99,
                                              "restaurantName": "And doners for all"
                                            }"""
                            ))})
            @RequestBody DishDto dishDto) {
        return dishService.save(id, dishId, dishDto);
    }

    @Operation(summary = "Delete dish",
            description = "Allows to delete specific dish of the restaurant",
            responses = {@ApiResponse(responseCode = "204", description = "Returns no content",
                    content = @Content),
                    @ApiResponse(responseCode = "404",
                            description = "Restaurant with id \"X\"  or dish with id \"Y\" not found",
                            content = @Content)})

    @DeleteMapping("{id}/dishes/{dishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDish(
            @Parameter(description = "Restaurant's id from database", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dishes id from database", required = true)
            @PathVariable Long dishId) {
        dishService.deleteByRestaurantIdAndId(id, dishId);
    }

    @Operation(summary = "Find restaurant's reviews",
            description = "Returns all reviews specific restaurant",
            responses = {@ApiResponse(responseCode = "200", description = "List of reviews",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = @ExampleObject(value =
                                    """
                                            [
                                              "Best restaurant ever!!",
                                              "Nice and quiet restaurant))"
                                            ]"""))}),
                    @ApiResponse(responseCode = "404",
                            description = "Restaurant with id \"X\" not found",
                            content = @Content)})

    @GetMapping("{id}/reviews")
    public List<String> findAllReviews(
            @Parameter(description = "Restaurant's id from database", required = true)
            @PathVariable Long id) {
        return restaurantService.findAllReviews(id);
    }

    @Operation(summary = "Delete review method",
            description = "Allows to delete review of specific restaurant",
            responses = {@ApiResponse(responseCode = "204", description = "Returns no content",
                    content = @Content),
                    @ApiResponse(responseCode = "404",
                            description = "Restaurant with id \"X\" not found",
                            content = @Content)})

    @DeleteMapping("{id}/reviews")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(
            @Parameter(description = "Restaurant's id from database", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User's review",
                    required = true,
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "My deletable review"))})
            @RequestBody String review) {
        restaurantService.deleteReview(id, review);
    }

}
