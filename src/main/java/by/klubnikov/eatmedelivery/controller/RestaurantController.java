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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("restaurants")
@SecurityRequirement(name = "JWT")
@Tag(name = "Restaurants controller",
        description = "This controller for viewing restaurants and it's dishes. " +
                "Also, users have possibility to leave review about restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    private final DishService dishService;

    @Operation(summary = "Find all restaurants",
            description = "This method returns all restaurants, stored in database",
            responses = @ApiResponse(responseCode = "200",
                    description = "List of restaurants with addresses",
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
                                            ]"""
                            ))}))
    @GetMapping
    public List<RestaurantListView> findAll() {
        return restaurantService.findAll();
    }

    @Operation(summary = "Find restaurant",
            description = "This method returns one restaurant from database if present",
            responses = {@ApiResponse(responseCode = "200",
                    description = "Obtained restaurant, which has its address and description fields",
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

    @Operation(summary = "Find dishes of a restaurant",
            description = "Method returns list of dishes of one specific restaurant",
            responses = {@ApiResponse(responseCode = "200",
                    description = "Returns list of dishes with its names and prices",
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

    @Operation(summary = "Find dish",
            description = "Returns specific dish of specific restaurant",
            responses = {@ApiResponse(responseCode = "200",
                    description = "Returns dish with it's name, description, price and restaurant's name",
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
    public DishDto findByRestaurantIdAndDishId(
            @Parameter(description = "Restaurant's id from database", required = true)
            @PathVariable Long id,
            @Parameter(description = "Dishes id from database", required = true)
            @PathVariable Long dishId) {
        return dishService.findByRestaurantIdAndId(id, dishId);
    }

    @Operation(summary = "Find restaurant's reviews",
            description = "This method returns all reviews specific restaurant",
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

    @Operation(summary = "Add review",
            description = "Allows to add new review to specific restaurant",
            responses = {@ApiResponse(responseCode = "201", description = "List of reviews with new one",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = @ExampleObject(value =
                                    """
                                            [
                                              "Best restaurant ever!!",
                                              "Nice and quiet restaurant))",
                                              "My new review"
                                            ]"""))}),
                    @ApiResponse(responseCode = "404",
                            description = "Restaurant with id \"X\" not found",
                            content = @Content)})

    @PostMapping("{id}/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> saveReview(
            @Parameter(description = "Restaurant's id from database", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User's review",
                    required = true,
                    content = {@Content(mediaType = "text/plain",
                            schema = @Schema(implementation = String.class),
                            examples = @ExampleObject(value = "My new review"))})
            @RequestBody String review) {
        return restaurantService.saveReview(id, review);
    }

    @Operation(summary = "Update review",
            description = "Allows to update specific review of the restaurant",
            responses = {@ApiResponse(responseCode = "200", description = "List of reviews with new one",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = String.class)),
                            examples = @ExampleObject(value =
                                    """
                                            [
                                              "Best restaurant ever!!",
                                              "Nice and quiet restaurant))",
                                              "My updated review"
                                            ]"""))}),
                    @ApiResponse(responseCode = "404",
                            description = "Restaurant with id \"X\" not found",
                            content = @Content)})

    @PutMapping("{id}/reviews")
    public List<String> updateReview(
            @Parameter(description = "Restaurant's id from database", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Review form, which has two fields: updatable review " +
                            "and updated review",
                    required = true,
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateReviewForm.class),
                            examples = @ExampleObject(value =
                                    """
                                            {
                                              "review": "My review",
                                              "updatedReview": "My updated review"
                                            }"""))})
            @RequestBody UpdateReviewForm form) {
        return restaurantService.updateReview(id, form);
    }

    @Operation(summary = "Delete review",
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
