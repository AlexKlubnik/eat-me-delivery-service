package by.klubnikov.eatmedelivery.controller;

import by.klubnikov.eatmedelivery.dto.UserAuthRequest;
import by.klubnikov.eatmedelivery.dto.UserAuthResponse;
import by.klubnikov.eatmedelivery.dto.UserPageView;
import by.klubnikov.eatmedelivery.dto.UserRegistration;
import by.klubnikov.eatmedelivery.entity.User;
import by.klubnikov.eatmedelivery.jwt.JwtTokenUtil;
import by.klubnikov.eatmedelivery.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@SecurityRequirement(name = "JWT")
public class AuthController {

    private final UserService service;

    @PostMapping("registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserPageView createUser(@RequestBody UserRegistration user) {
        return service.save(user);
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.CREATED)
    public UserAuthResponse login(@RequestBody UserAuthRequest authRequest) {
        String token = service.getTokenForUserIfExists(authRequest);
        return new UserAuthResponse(token);
    }
}
