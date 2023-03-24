package by.klubnikov.eatmedelivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
@Table(name = "delivety_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String city;


    private String street;

    @Min(0)
    private int houseNumber;

    @Min(0)
    private int buildingNumber;

    @Min(0)
    private int apartmentNumber;

}
