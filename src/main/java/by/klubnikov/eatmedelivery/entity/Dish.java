package by.klubnikov.eatmedelivery.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@Table(name = "delivery_dishes")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name of dish can't be empty.")
    private String name;

    @Column(length = 500)
    private String description;

    private double price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Restaurant restaurant;

}
