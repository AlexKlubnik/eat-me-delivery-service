package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.UserConverter;
import by.klubnikov.eatmedelivery.dto.UserRegistration;
import by.klubnikov.eatmedelivery.entity.Role;
import by.klubnikov.eatmedelivery.entity.User;
import by.klubnikov.eatmedelivery.error.ResourceNotFoundException;
import by.klubnikov.eatmedelivery.jwt.JwtTokenUtil;
import by.klubnikov.eatmedelivery.repository.RoleRepository;
import by.klubnikov.eatmedelivery.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserRepository repository;
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private UserConverter converter;

    private JwtTokenUtil tokenUtil;

    private UserService service;
    private User user;
    private UserRegistration userRegistration;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        tokenUtil = new JwtTokenUtil();
        passwordEncoder = new BCryptPasswordEncoder();
        converter = new UserConverter();
        service = spy(new UserService(repository, roleRepository, passwordEncoder, converter, tokenUtil));

        user = new User();
        userRegistration = new UserRegistration();
    }

    @Test
    void findByLogin() {
        when(repository.findByLogin("Login")).thenReturn(Optional.of(new User()));
        service.findByLogin("Login");
        verify(repository).findByLogin("Login");

    }

    @Test
    void save() {
        userRegistration.setPassword("String123456");
        when(roleRepository.findById(2L)).thenReturn(Optional.of(new Role()));
        when(repository.save(any())).thenReturn(new User());
        InOrder inOrder = inOrder(roleRepository, repository);
        service.save(userRegistration);

        verify(roleRepository).findById(2L);
        verify(repository).save(any());
        verifyNoMoreInteractions(roleRepository, repository);

        inOrder.verify(roleRepository).findById(2L);
        inOrder.verify(repository).save(any());
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void findByLoginAndCheckPassword() {
        String encodedPass = passwordEncoder.encode("String123456");
        user.setPassword(encodedPass);
        when(service.findByLogin("Login")).thenReturn(Optional.of(user));
        Optional<User> expected = service.findByLoginAndCheckPassword("Login", "String123456");

        verify(service).findByLogin("Login");
        assertThrows(ResourceNotFoundException.class,
                () -> service.findByLoginAndCheckPassword("WrongLogin", "String123456"));
        assertEquals(expected, Optional.of(user));
        assertEquals(Optional.empty(), service.findByLoginAndCheckPassword("Login", "WrongPass"));
    }

}