package by.klubnikov.eatmedelivery.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryServiceError {
    private int statusCode;
    private String message;
}
