package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.UserConverter;
import by.klubnikov.eatmedelivery.dto.UserAuthRequest;
import by.klubnikov.eatmedelivery.dto.UserPageView;
import by.klubnikov.eatmedelivery.dto.UserRegistration;
import by.klubnikov.eatmedelivery.entity.User;
import by.klubnikov.eatmedelivery.error.ResourceNotFoundException;
import by.klubnikov.eatmedelivery.repository.RoleRepository;
import by.klubnikov.eatmedelivery.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserConverter converter;


    public UserPageView save(UserRegistration user) {
        User savableUser = converter.convert(user);
        savableUser.setRole(roleRepository.findById(1L)
                .orElseThrow());
        savableUser.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = repository.save(savableUser);
        return converter.convert(savedUser);
    }

    public Optional<User> findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public Optional<User> findByLoginAndCheckPassword(String login, String password) {
        User user = findByLogin(login).orElseThrow(() -> new ResourceNotFoundException(
                "User with login " + login + " not found"));
        if (passwordEncoder.matches(password, user.getPassword())) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public User getUserForTokenIfExists(UserAuthRequest authRequest) {
        return findByLoginAndCheckPassword(authRequest.getLogin(), authRequest.getPassword())
                .orElseThrow();
    }

}
