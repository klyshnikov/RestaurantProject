package ru.hse.restaurant.project.exceptions;

public class PriceLessThanZeroException extends Throwable {
    public String message;

    public PriceLessThanZeroException(String message) {
        this.message = message;
    }
}
