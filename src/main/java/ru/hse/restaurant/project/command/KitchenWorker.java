package ru.hse.restaurant.project.command;

import ru.hse.restaurant.project.decorator.OrderDecorator;
import ru.hse.restaurant.project.entity.Dish;

public class KitchenWorker implements Runnable {
    Dish dish;
    OrderDecorator orderDecorator;

    public KitchenWorker(Dish dish, OrderDecorator orderDecorator) {
        this.dish = dish;
        this.orderDecorator = orderDecorator;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(dish.getTimeToCook() * 1000L);
        } catch (InterruptedException e) {
            return;
        }

        if (!Thread.currentThread().isInterrupted()) {
            orderDecorator.increaseNumberOfCookedDishes();
        }
    }
}
