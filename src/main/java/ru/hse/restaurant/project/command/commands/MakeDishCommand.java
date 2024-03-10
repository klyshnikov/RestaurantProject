package ru.hse.restaurant.project.command.commands;

import jdk.jfr.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hse.restaurant.project.decorator.OrderDecorator;
import ru.hse.restaurant.project.command.OrderReceiver;
import ru.hse.restaurant.project.command.Kitchen;
import ru.hse.restaurant.project.entity.Dish;

@AllArgsConstructor
public class MakeDishCommand implements Command {
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
        receiver.makeDish(dish, orderDecorator);
    }

}
