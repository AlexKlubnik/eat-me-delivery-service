package by.klubnikov.eatmedelivery.controller;

import by.klubnikov.eatmedelivery.dto.UserAuthRequest;
import by.klubnikov.eatmedelivery.dto.UserAuthResponse;
import by.klubnikov.eatmedelivery.dto.UserPageView;
import by.klubnikov.eatmedelivery.dto.UserRegistration;
import by.klubnikov.eatmedelivery.entity.User;
import by.klubnikov.eatmedelivery.jwt.JwtTokenUtil;
import by.klubnikov.eatmedelivery.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;
    private final JwtTokenUtil tokenUtil;

    @PostMapping("registration")
    @ResponseStatus(HttpStatus.CREATED)
    public UserPageView createUser(@RequestBody UserRegistration user) {
        return service.save(user);
    }

    @PostMapping("login")
    public UserAuthResponse login(@RequestBody UserAuthRequest authRequest) {
        User user = service.getUserForTokenIfExists(authRequest);
        return new UserAuthResponse(tokenUtil.generateToken(user.getLogin()));
    }

//    @PostMapping("login")
//    public AuthResponse login(@RequestBody AuthRequest authRequest) {
//        User user = userService.getTokenForUserIfExists(authRequest);
//        return new AuthResponse(tokenUtil.generateToken(user.getLogin()));
//    }
}
