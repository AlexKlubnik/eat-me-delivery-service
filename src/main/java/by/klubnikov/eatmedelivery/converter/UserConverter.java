package by.klubnikov.eatmedelivery.converter;

import by.klubnikov.eatmedelivery.dto.UserPageView;
import by.klubnikov.eatmedelivery.dto.UserRegistration;
import by.klubnikov.eatmedelivery.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    /**
     * Converts userDto from registration form to user which should be saved to database
     * @param userRegistration - Dto collected from registration form
     * @return object to store in database
     */

    public User convert(UserRegistration userRegistration) {
        User user = new User();
        user.setLogin(userRegistration.getLogin());
        user.setPassword(userRegistration.getPassword());
        user.setName(userRegistration.getName());
        user.setSurname(userRegistration.getSurname());
        user.setAge(userRegistration.getAge());
        user.setPhoneNumbers(userRegistration.getPhoneNumbers());
        return user;
    }

    /**
     * Converts user from database to userDto which should be shown on user's page
     * @param user - object collected from database
     * @return Dto to view at user's page
     */

    public UserPageView convert(User user) {
        UserPageView userPageView = new UserPageView();
        userPageView.setLogin(user.getLogin());
        userPageView.setName(user.getName());
        userPageView.setSurname(user.getSurname());
        userPageView.setAge(user.getAge());
        userPageView.setPhoneNumbers(user.getPhoneNumbers());
        return userPageView;
    }


}
