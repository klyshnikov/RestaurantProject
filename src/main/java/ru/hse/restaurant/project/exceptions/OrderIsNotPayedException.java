package ru.hse.restaurant.project.exceptions;

public class OrderIsNotPayedException extends Throwable {
    public String message;

    public OrderIsNotPayedException(String message) {
        this.message = message;
    }
}
