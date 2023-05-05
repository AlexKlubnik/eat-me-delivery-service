package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.converter.UserConverter;
import by.klubnikov.eatmedelivery.dto.UserPageView;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {
    public static final String LOGIN = "alex89";
    public static final String PASSWORD = "123456password";
    public static final String INVALID_LOGIN = "WrongLogin";
    public static final String INVALID_PASSWORD = "WrongPass";
    private UserRepository repository;
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private UserConverter converter;

    private JwtTokenUtil tokenUtil;

    private UserService service;
    private User user;
    private Role role;
    private UserRegistration userRegistration;
    private UserPageView userPageView;

    @BeforeEach
    void setUp() {
        repository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        tokenUtil = new JwtTokenUtil();
        passwordEncoder = new BCryptPasswordEncoder();
        converter = new UserConverter();
        service = spy(new UserService(repository, roleRepository, passwordEncoder, converter, tokenUtil));

        initUser();
        initRole();
        initUserRegistration();
        initUserPageView();
    }

    private void initUser() {
        user = new User();
        user.setId(1L);
        user.setRole(role);
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setName("Alex");
        user.setSurname("Klubnikov");
        user.setAge(34);
        user.setPhoneNumbers(List.of("+375257865544"));
        user.setOrders(new ArrayList<>());
    }

    private void initRole() {
        role = new Role();
        role.setId(2L);
        role.setName("ROLE_USER");
    }

    private void initUserRegistration() {
        userRegistration = new UserRegistration();
        userRegistration.setLogin(LOGIN);
        userRegistration.setPassword(PASSWORD);
        userRegistration.setName("Alex");
        userRegistration.setSurname("Klubnikov");
        userRegistration.setAge(34);
        userRegistration.setPhoneNumbers(List.of("+375257865544"));
    }

    private void initUserPageView() {
        userPageView = new UserPageView();
        userPageView.setLogin(LOGIN);
        userPageView.setName("Alex");
        userPageView.setSurname("Klubnikov");
        userPageView.setAge(34);
        userPageView.setPhoneNumbers(List.of("+375257865544"));
    }

    @Test
    void findByLogin_WhenUserExist() {
        when(repository.findByLogin(LOGIN)).thenReturn(Optional.of(user));
       Optional<User> expected = service.findByLogin(LOGIN);
        verify(repository).findByLogin(LOGIN);
        verifyNoMoreInteractions(repository);
        assertEquals(expected, Optional.of(user));
    }

    @Test
    void findByLogin_WhenUserNotExist() {
        Optional<User> expected = service.findByLogin("Login");
        verify(repository).findByLogin("Login");
        verifyNoMoreInteractions(repository);
        assertEquals(expected, Optional.empty());
    }

    @Test
    void save_WhenRoleExist() {
        when(roleRepository.findById(2L)).thenReturn(Optional.of(role));
        when(repository.save(any())).thenReturn(user);
        InOrder inOrder = inOrder(roleRepository, repository);
        UserPageView expected = service.save(userRegistration);

        verify(roleRepository).findById(2L);
        verify(repository).save(any());
        verifyNoMoreInteractions(roleRepository, repository);

        inOrder.verify(roleRepository).findById(2L);
        inOrder.verify(repository).save(any());
        inOrder.verifyNoMoreInteractions();

        assertEquals(expected, converter.convert(user));

    }

    @Test
    void save_WhenRoleNotExist() {
        assertThrows(NoSuchElementException.class, () -> service.save(userRegistration));
    }

    @Test
    void findByLoginAndCheckPassword_WhenUserExist_AndPasswordMatch() {
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        when(repository.findByLogin(LOGIN)).thenReturn(Optional.of(user));
        Optional<User> expected = service.findByLoginAndCheckPassword(LOGIN, PASSWORD);

        verify(repository).findByLogin(LOGIN);
        verifyNoMoreInteractions(repository);
        assertEquals(expected, Optional.of(user));
    }

    @Test
    void findByLoginAndCheckPassword_WhenUserNotExist() {
        assertThrows(ResourceNotFoundException.class,
                () -> service.findByLoginAndCheckPassword(INVALID_LOGIN, PASSWORD));
    }

    @Test
    void findByLoginAndCheckPassword_WhenUserExist_AndPasswordNotMatch() {
        String encodedPass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPass);
        when(repository.findByLogin(LOGIN)).thenReturn(Optional.of(user));
        assertEquals(service.findByLoginAndCheckPassword(LOGIN, INVALID_PASSWORD), Optional.empty());
    }
}