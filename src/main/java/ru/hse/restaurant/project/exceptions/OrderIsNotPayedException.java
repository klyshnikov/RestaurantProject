package ru.hse.restaurant.project.exceptions;

public class OrderIsNotPayedException extends RuntimeException {
    public OrderIsNotPayedException(String message) {
        super("Чтобы получить заказ, надо заплатить деньги (pay-for-order). Деньги я не дам");
    }

    public OrderIsNotPayedException() {
        super("Чтобы получить заказ, надо заплатить деньги (pay-for-order). Деньги я не дам");
    }
}
