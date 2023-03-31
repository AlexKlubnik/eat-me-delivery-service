package by.klubnikov.eatmedelivery.service;

import by.klubnikov.eatmedelivery.entity.User;
import by.klubnikov.eatmedelivery.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService service;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = service.findByLogin(login).orElseThrow(
                () -> new UsernameNotFoundException(login + " not found"));
        return CustomUserDetails.getUserDetailsFromUser(user);
    }
}
