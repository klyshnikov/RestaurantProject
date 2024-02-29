package ru.hse.restaurant.project.command.commands;

import ru.hse.restaurant.project.decorator.OrderDecorator;
import ru.hse.restaurant.project.command.OrderReceiver;
import ru.hse.restaurant.project.command.Kitchen;
import ru.hse.restaurant.project.entity.Dish;

public class MakeDishCommand implements Command, Runnable {
    Dish dish;
    OrderDecorator orderDecorator;
    OrderReceiver receiver;

    public MakeDishCommand(Dish dish, OrderDecorator orderDecorator) {
        this.dish = dish;
        this.orderDecorator = orderDecorator;
        receiver = new Kitchen();
    }

    @Override
    public void Execute() throws InterruptedException {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        try {
            receiver.makeDish(dish, orderDecorator);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
