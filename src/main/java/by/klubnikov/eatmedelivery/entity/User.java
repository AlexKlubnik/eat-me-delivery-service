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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login;

    private String password;

    private String name;

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

    private int age;

    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }

}
