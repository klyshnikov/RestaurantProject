package ru.hse.restaurant.project.exceptions;

public class OrderIsNotAlreadyCookedException extends RuntimeException {
    public OrderIsNotAlreadyCookedException(String message) {
        super("Заказ еще не приготовился! Ждите ... ");
    }

    public OrderIsNotAlreadyCookedException() {
        super("Заказ еще не приготовился! Ждите ... ");
    }
}
