package by.klubnikov.eatmedelivery.dto;

import lombok.Data;

@Data
public class UserAuthRequest {
    private String login;
    private String password;
}
