package ru.hse.restaurant.project.command;

import ru.hse.restaurant.project.decorator.OrderDecorator;
import ru.hse.restaurant.project.command.commands.Command;
import ru.hse.restaurant.project.command.commands.MakeDishCommand;
import ru.hse.restaurant.project.exceptions.OrderIsNotCreatedYetException;
import ru.hse.restaurant.project.repository.DishRepository;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.Order;
import ru.hse.restaurant.project.exceptions.OrderIsNotAlreadyCookedException;
import ru.hse.restaurant.project.exceptions.OrderIsNotPayedException;

import java.util.List;
import java.util.Map;

public class Waiter implements OrderInvoker {
    private final DishRepository dishRepository;

    public Waiter(DishRepository dishDao) {
        this.dishRepository = dishDao;
    }

    @Override
    public List<Dish> getActualMenu() {
        return dishRepository.getAllDishes();
    }

    @Override
    public void addDish(String dishName, OrderDecorator orderDecorator) {
        orderDecorator.addDish(dishRepository.getDish(dishName));
    }

    @Override
    public void prepare(OrderDecorator orderDecorator) throws InterruptedException {
        for (Map.Entry<Dish, Integer> orderPosition : orderDecorator.getOrder().getDish().entrySet()) {
            for (int i = 0; i < orderPosition.getValue(); ++i) {
                Command prepareThisDishCommand = new MakeDishCommand(orderPosition.getKey(), orderDecorator);
                prepareThisDishCommand.Execute();
            }
        }
    }

    @Override
    public void pay(OrderDecorator orderDecorator) {
        orderDecorator.makePayed();
    }

    @Override
    public Order getOrder(OrderDecorator orderDecorator)
            throws OrderIsNotPayedException, OrderIsNotAlreadyCookedException, OrderIsNotCreatedYetException {

        // Тут напрашивается цепочка обязанностей, но по мне она тут лишняя,
        // цепочка проверок не такая и большая.
        if (!orderDecorator.isExist()) {
            throw new OrderIsNotCreatedYetException("Order is not created. Create it!");
        }

        if (!orderDecorator.isCooked()) {
            throw new OrderIsNotAlreadyCookedException("Order is not cooked! Wait ...");
        }

        if (!orderDecorator.isPayed()) {
            throw new OrderIsNotPayedException("Order is not payed!! You need to pay");
        }

        return orderDecorator.getOrder();
    }

    @Override
    public void cansel(OrderDecorator orderDecorator) {
        orderDecorator.setNotExisted();
    }

    @Override
    public void createOrder(OrderDecorator orderDecorator) {
        orderDecorator.setExisted();
    }
}
