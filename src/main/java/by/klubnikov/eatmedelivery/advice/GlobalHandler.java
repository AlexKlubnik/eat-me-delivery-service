package by.klubnikov.eatmedelivery.advice;

import by.klubnikov.eatmedelivery.error.DeliveryServiceError;
import by.klubnikov.eatmedelivery.error.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalHandler {
    @ExceptionHandler
    public ResponseEntity<DeliveryServiceError> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.info(e.getMessage(), e);
        return new ResponseEntity<>(new DeliveryServiceError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

}
