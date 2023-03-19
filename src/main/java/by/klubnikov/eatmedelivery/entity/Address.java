package by.klubnikov.eatmedelivery.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@Entity
@Table(name = "delivety_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(min=2, max=100)
    private String city;

    @Length(min=2, max=100)
    private String street;

    @Min(1)
    private int houseNumber;

    @Min(0)
    private int buildingNumber;

    @Min(0)
    private int apartmentNumber;

}
