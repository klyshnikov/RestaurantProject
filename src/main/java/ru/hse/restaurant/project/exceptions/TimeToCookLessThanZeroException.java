package ru.hse.restaurant.project.exceptions;

public class TimeToCookLessThanZeroException extends Throwable {
    public String message;

    public TimeToCookLessThanZeroException(String message) {
        this.message = message;
    }
}
