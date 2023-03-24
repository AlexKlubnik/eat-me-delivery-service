package by.klubnikov.eatmedelivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "delivery_users")
public class User {

    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.[@#$%^&-+=()]*)(?=\\S+$).{6,20}$";
    public static final String LOGIN_PATTERN = "^(?=.*[a-zA-Z])(?=.[0-9]*).{2,20}$";
    public static final String NAME_AND_SURNAME_PATTERN = "^[a-zA-Z]{2,20}$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
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

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;


    @ElementCollection
    private List<String> phoneNumbers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();

    @Min(value = 18, message = "Your age should not be under 18 years old.")
    private int age;

    public void addOrder(Order order){
        orders.add(order);
        order.setUser(this);
    }

}
