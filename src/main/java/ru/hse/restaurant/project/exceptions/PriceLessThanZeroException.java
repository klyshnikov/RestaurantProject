package ru.hse.restaurant.project.exceptions;

public class PriceLessThanZeroException extends RuntimeException {

    public PriceLessThanZeroException(String message) {
        super("Вы нарушили закон РФ. Цена не должна быть меньше 0");
    }

    public PriceLessThanZeroException() {
        super("Вы нарушили закон РФ. Цена не должна быть меньше 0");
    }
}
