package ru.hse.restaurant.project.command;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hse.restaurant.project.command.commands.CanselOrderCommand;
import ru.hse.restaurant.project.decorator.OrderDecorator;
import ru.hse.restaurant.project.command.commands.Command;
import ru.hse.restaurant.project.command.commands.MakeDishCommand;
import ru.hse.restaurant.project.decorator.OrderDecoratorImpl;
import ru.hse.restaurant.project.decorator.OrderState;
import ru.hse.restaurant.project.exceptions.OrderIsNotCreatedYetException;
import ru.hse.restaurant.project.repository.DishRepository;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.Order;
import ru.hse.restaurant.project.exceptions.OrderIsNotAlreadyCookedException;
import ru.hse.restaurant.project.exceptions.OrderIsNotPayedException;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class Waiter implements OrderInvoker {
    private final DishRepository dishRepository;

    @Override
    public List<Dish> getActualMenu() {
        return dishRepository.getAllDishes();
    }

    @Override
    public void addDish(String dishName, OrderDecorator orderDecorator) {
        if (orderDecorator.getOrderState() == OrderState.Stage2_Creating) {
            orderDecorator.addDish(dishRepository.getDish(dishName));
        } else if (orderDecorator.getOrderState() == OrderState.Stage1_NotExist) {
            throw new RuntimeException("Заказ не создан");
        } else {
            throw new RuntimeException("Уже поздно добавлять блюда в заказ");
        }
    }

    @Override
    public void prepare(OrderDecorator orderDecorator) throws InterruptedException {
        if (orderDecorator.getOrderState() == OrderState.Stage1_NotExist) {
            throw new OrderIsNotCreatedYetException();
        }

        if (orderDecorator.getOrderState() == OrderState.Stage3_Preparing) {
            throw new RuntimeException("Заказ уже готовится!");
        }

        if (orderDecorator.getOrderState() == OrderState.Stage4_Ready
            || orderDecorator.getOrderState() == OrderState.Stage5_Payed) {
            throw new RuntimeException("Заказ уже готов!");
        }

        if (orderDecorator.getOrder().getDish().isEmpty()) {
            throw new RuntimeException("Заказ пустой. Готовить нечего.");
        }
        orderDecorator.setState3_Preparing();

        for (Map.Entry<Dish, Integer> orderPosition : orderDecorator.getOrder().getDish().entrySet()) {
            for (int i = 0; i < orderPosition.getValue(); ++i) {
                var makeDishCommand = new MakeDishCommand(orderPosition.getKey(), orderDecorator);
                makeDishCommand.Execute();
            }
        }
    }

    @Override
    public void pay(OrderDecorator orderDecorator) throws OrderIsNotAlreadyCookedException {
        if (orderDecorator.getOrderState() == OrderState.Stage1_NotExist) {
            throw new OrderIsNotCreatedYetException();
        }

        if (orderDecorator.getOrderState() == OrderState.Stage2_Creating) {
            throw new RuntimeException("Заказ еще на этапе формирования. Нельзя оплатить");
        }

        if (orderDecorator.getOrderState() != OrderState.Stage4_Ready) {
            throw new OrderIsNotAlreadyCookedException();
        }

        orderDecorator.setState5_Payed();
    }

    @Override
    public Order getOrder(OrderDecorator orderDecorator)
            throws OrderIsNotPayedException, OrderIsNotCreatedYetException {

        // Тут напрашивается цепочка обязанностей, но по мне она тут лишняя,
        // цепочка проверок не такая и большая.
        if (orderDecorator.getOrderState() != OrderState.Stage5_Payed) {
            throw new RuntimeException("Заказ надо сделать, дождаться и оплатить!");
        }

        return orderDecorator.getOrder();
    }

    @Override
    public void cansel(OrderDecorator orderDecorator) {
        if (orderDecorator.getOrderState() == OrderState.Stage4_Ready
            || orderDecorator.getOrderState() == OrderState.Stage5_Payed) {
            throw new RuntimeException("Уже поздно отменять - заказ приготовлен");
        }

        new CanselOrderCommand().Execute();
        orderDecorator.setDefault();
    }

    @Override
    public void createOrder(OrderDecorator orderDecorator) {
        if (orderDecorator.getOrderState() == OrderState.Stage1_NotExist) {
            orderDecorator.setState2_Creating();
        } else {
            throw new RuntimeException("Заказ уже создан!");
        }
    }

    @Override
    public String getOrderInfo(OrderDecorator orderDecorator) {
        StringBuilder result = new StringBuilder("Ваш заказ: \n");
        for (Map.Entry<Dish, Integer> orderPosition : orderDecorator.getOrder().getDish().entrySet()) {
            result.append(orderPosition.getKey().getName());
            result.append(" кол-во ");
            result.append(orderPosition.getValue().toString());
            result.append("\n");
        }

        result.append("Статус: ");
        switch (orderDecorator.getOrderState().name()) {
            case ("Stage1_NotExist") -> result.append("Заказ не создан");
            case ("Stage2_Creating") -> result.append("Заказ на стадии сборки. Можете добавлять блюда");
            case ("Stage3_Preparing") -> result.append("Заказ готовится на кухне");
            case ("Stage4_Ready") -> result.append("Заказ готов и ожидает оплаты");
            case ("Stage5_Payed") -> result.append("Заказ оплачен - можете получить его");
            default -> {}
        }
        result.append("\n");

        result.append("Приготовлено блюд: ");
        result.append(orderDecorator.getNumberOfCookedDishes());

        return result.toString();
    }
}
