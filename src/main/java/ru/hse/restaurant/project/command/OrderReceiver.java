package ru.hse.restaurant.project.command;

import ru.hse.restaurant.project.decorator.OrderDecorator;
import ru.hse.restaurant.project.entity.Dish;

public interface OrderReceiver {
    void makeDish(Dish dish, OrderDecorator orderDecorator) throws InterruptedException;
    void stopCooking();
}
