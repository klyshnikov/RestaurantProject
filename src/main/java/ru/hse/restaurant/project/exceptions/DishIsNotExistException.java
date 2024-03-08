package ru.hse.restaurant.project.exceptions;

public class DishIsNotExistException extends RuntimeException {
    public DishIsNotExistException(String message) {
        super("Заказ еще не приготовился! Ждите ... ");
    }

    public DishIsNotExistException() {
        super("Заказ еще не приготовился! Ждите ... ");
    }

}
