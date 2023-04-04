package by.klubnikov.eatmedelivery.controller;

import by.klubnikov.eatmedelivery.dto.UserAuthRequest;
import by.klubnikov.eatmedelivery.dto.UserAuthResponse;
import by.klubnikov.eatmedelivery.dto.UserPageView;
import by.klubnikov.eatmedelivery.dto.UserRegistration;
import by.klubnikov.eatmedelivery.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
@Tag(name = "Registration and authentication controller",
        description = "This controller for registration and authentication of users")
public class AuthController {

    private final UserService service;

    @Operation(summary = "Registration method",
            description = "Takes parameters from registration form, creates user-entity and stores it to database",
            responses = {@ApiResponse(responseCode = "201", description = "Returns UserPageView dto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserPageView.class),
                            examples = @ExampleObject(value =
                                    """
                                            {
                                              "login": "alex89",
                                              "name": "Alex",
                                              "surname": "Klubnikov",
                                               "age": 18,
                                               "phoneNumbers": [
                                                "+75782556455"
                                              ]}"""))})})
    @PostMapping("registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserPageView createUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User retrieved from registration form",
            required = true,
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserRegistration.class),
                    examples = @ExampleObject(value =
                            """
                                    {
                                      "login": "alex89",
                                      "password": "String123456",
                                      "name": "Alex",
                                      "surname": "Klubnikov",
                                       "age": 18,
                                       "phoneNumbers": [
                                        "+75782556455"
                                      ]}"""))})
                                   @RequestBody UserRegistration user) {
        return service.save(user);
    }

    @Operation(summary = "Authentication method",
            description = "Takes parameters from login form and produces jwt token for getting access to " +
                    "methods of restaurant or restaurant-manager controllers according to user's role",
            responses = {@ApiResponse(responseCode = "201", description = "Returns UserPageView dto",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserAuthResponse.class))})})

    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    public UserAuthResponse login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User retrieved from login form",
            required = true,
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = UserAuthRequest.class),
                    examples = @ExampleObject(value =
                            "{\n" +
                                    "  \"login\": \"alex89\",\n" +
                                    "  \"password\": \"String123456\""+
                                    "}"))})
                                  @RequestBody UserAuthRequest authRequest) {
        String token = service.getTokenForUserIfExists(authRequest);
        return new UserAuthResponse(token);
    }
}
