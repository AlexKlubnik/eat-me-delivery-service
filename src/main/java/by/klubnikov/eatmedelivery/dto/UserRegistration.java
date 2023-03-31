package by.klubnikov.eatmedelivery.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserRegistration {
    private String login;
    private String password;
    private String name;
    private String surname;
    private int age;
    private List<String> phoneNumbers;

}
