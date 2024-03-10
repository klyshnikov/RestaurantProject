package ru.hse.restaurant.project.command.commands;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hse.restaurant.project.command.Kitchen;
import ru.hse.restaurant.project.command.OrderReceiver;
import ru.hse.restaurant.project.decorator.OrderDecorator;
import ru.hse.restaurant.project.decorator.OrderDecoratorImpl;

@AllArgsConstructor
public class CanselOrderCommand implements Command {
    OrderReceiver orderReceiver;

    public CanselOrderCommand() {
        orderReceiver = new Kitchen();
    }

    @Override
    public void Execute() {
        orderReceiver.stopCooking();
    }
}
