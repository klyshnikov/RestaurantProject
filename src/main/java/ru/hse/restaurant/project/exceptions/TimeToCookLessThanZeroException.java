package ru.hse.restaurant.project.exceptions;

public class TimeToCookLessThanZeroException extends RuntimeException {

    public TimeToCookLessThanZeroException(String message) {
        super("Вы нарушили закон физики. Время выполнения заказа не должно быть меньше 0");
    }

    public TimeToCookLessThanZeroException() {
        super("Вы нарушили закон физики. Время выполнения заказа не должно быть меньше 0");
    }
}
