package ru.hse.restaurant.project.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderIsNotCreatedYetException extends RuntimeException {

    public OrderIsNotCreatedYetException(String message) {
        super("bad");
    }
}
