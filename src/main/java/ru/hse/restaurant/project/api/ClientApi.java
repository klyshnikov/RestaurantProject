package ru.hse.restaurant.project.api;

import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.Order;
import ru.hse.restaurant.project.exceptions.OrderIsNotAlreadyCookedException;
import ru.hse.restaurant.project.exceptions.OrderIsNotCreatedYetException;
import ru.hse.restaurant.project.exceptions.OrderIsNotPayedException;

import java.util.List;

public interface ClientApi {
    public List<Dish> getMenu();

    public void createOrder();

    public void addInOrder(String dishName);

    public void addInOrder(String dishName, int amount);

    public void prepareOrder() throws InterruptedException;

    public void payForOrder();

    public Order getOrder() throws OrderIsNotAlreadyCookedException, OrderIsNotPayedException, OrderIsNotCreatedYetException;

    public void canselOrder();
}
