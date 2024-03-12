package ru.hse.restaurant.project.exceptions;

public class DishIsNotFoundException extends RuntimeException {
    public DishIsNotFoundException() {super("Блюдо не найдено");}
}
