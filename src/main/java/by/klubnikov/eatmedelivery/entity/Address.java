package by.klubnikov.eatmedelivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "delivery_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "The name of city can not be empty")
    private String city;

    @NotEmpty(message = "The name of street can not be empty")
    private String street;

    @Min(value = 0, message = "This field can not be negative number")
    private int houseNumber;

    @Min(value = 0, message = "This field can not be negative number")
    private int buildingNumber;

    @Min(value = 0, message = "This field can not be negative number")
    private int apartmentNumber;

}
