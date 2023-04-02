package by.klubnikov.eatmedelivery.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class UserRegistration {
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.[@#$%^&-+=()]*)(?=\\S+$).{6,20}$";
    public static final String LOGIN_PATTERN = "^(?=.*[a-zA-Z])(?=.[0-9]*).{2,20}$";
    public static final String NAME_AND_SURNAME_PATTERN = "^[a-zA-Z]{2,20}$";

    @NotEmpty
    @Pattern(regexp = LOGIN_PATTERN, message = "Login consists from upper and lower case letters and may have\n" +
            "any digit. Length - from 2 to 20 characters.")
    private String login;

    @NotEmpty
    @Pattern(regexp = PASSWORD_PATTERN, message = "Password should have at least one number, one lower case letter,\n" +
            "one upper case letter, may have any special symbol such as \"@#$%^&-+=()\"\n" +
            "and doesn't have any whitespaces. Password length should be at least 6 characters\n" +
            "and less than 20.")
    private String password;

    @NotEmpty
    @Pattern(regexp = NAME_AND_SURNAME_PATTERN)
    private String name;

    @NotEmpty
    @Pattern(regexp = NAME_AND_SURNAME_PATTERN)
    private String surname;

    @Min(value = 18, message = "Your age should not be under 18 years old.")
    private int age;
    private List<String> phoneNumbers;

}
