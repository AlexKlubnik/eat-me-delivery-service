package by.klubnikov.eatmedelivery.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "delivery_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
