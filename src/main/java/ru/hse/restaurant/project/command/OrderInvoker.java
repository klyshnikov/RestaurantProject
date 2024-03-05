package ru.hse.restaurant.project.command;

import ru.hse.restaurant.project.decorator.OrderDecorator;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.Order;
import ru.hse.restaurant.project.exceptions.OrderIsNotAlreadyCookedException;
import ru.hse.restaurant.project.exceptions.OrderIsNotCreatedYetException;
import ru.hse.restaurant.project.exceptions.OrderIsNotPayedException;

import java.util.List;

public interface OrderInvoker {
    public List<Dish> getActualMenu();
    public void addDish(String dishName, OrderDecorator orderDecorator);
    public void prepare(OrderDecorator orderDecorator) throws InterruptedException;
    public void pay(OrderDecorator orderDecorator) throws OrderIsNotAlreadyCookedException;
    public Order getOrder(OrderDecorator orderDecorator) throws OrderIsNotPayedException, OrderIsNotAlreadyCookedException, OrderIsNotCreatedYetException;
    public void cansel(OrderDecorator orderDecorator);
    public void createOrder(OrderDecorator orderDecorator);

    String getOrderInfo(OrderDecorator orderDecorator);

}
