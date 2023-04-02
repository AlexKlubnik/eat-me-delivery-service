package by.klubnikov.eatmedelivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.context.annotation.Primary;

@Data
@Entity
@Table(name = "delivery_order_lines")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(value = 1, message = "You should order at least one dish")
    private int quantity;

    @ManyToOne
    @JoinColumn(name="dish_id")
    private Dish dish;

    private double price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Order order;

}
