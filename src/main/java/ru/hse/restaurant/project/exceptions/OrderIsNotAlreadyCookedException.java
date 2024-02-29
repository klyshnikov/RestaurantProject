package ru.hse.restaurant.project.exceptions;

public class OrderIsNotAlreadyCookedException extends Throwable {
    public String message;

    public OrderIsNotAlreadyCookedException(String message) {
        this.message = message;
    }
}
