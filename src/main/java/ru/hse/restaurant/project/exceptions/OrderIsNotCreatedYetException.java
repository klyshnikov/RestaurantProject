package ru.hse.restaurant.project.exceptions;


public class OrderIsNotCreatedYetException extends RuntimeException {

    public OrderIsNotCreatedYetException(String message) {
        super("Заказ еще не создан! Создайте его через create-order");
    }

    public OrderIsNotCreatedYetException() {
        super("Заказ еще не создан! Создайте его через create-order");
    }
}
