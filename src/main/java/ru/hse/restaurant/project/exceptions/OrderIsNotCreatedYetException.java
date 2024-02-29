package ru.hse.restaurant.project.exceptions;

public class OrderIsNotCreatedYetException extends Throwable {
    public String message;

    public OrderIsNotCreatedYetException(String message) {
        this.message = message;
    }
}
