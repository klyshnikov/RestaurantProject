package ru.hse.restaurant.project.command;

import ru.hse.restaurant.project.decorator.OrderDecorator;
import ru.hse.restaurant.project.entity.Dish;

public class Kitchen implements OrderReceiver {

    @Override
    public void makeDish(Dish dish, OrderDecorator orderDecorator) throws InterruptedException {
        Thread.sleep(dish.getTimeToCook() * 1000L);
        orderDecorator.increaseNumberOfCookedDishes();
    }
}
