package ru.hse.restaurant.project.decorator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.Order;

import java.util.Map;

@Component
@AllArgsConstructor
public class OrderDecoratorImpl implements OrderDecorator {
    private Order order;
    private int numberOfCookedDishes = 0;
    private OrderState orderState = OrderState.Stage1_NotExist;

    public OrderDecoratorImpl() {
        order = new Order();
    }

    @Override
    public void setState1_NotExist() {
        orderState = OrderState.Stage1_NotExist;
    }

    @Override
    public void setState2_Creating() {
        orderState = OrderState.Stage2_Creating;
    }

    @Override
    public void setState3_Preparing() {
        orderState = OrderState.Stage3_Preparing;
    }

    @Override
    public void setState4_Ready() {
        orderState = OrderState.Stage4_Ready;
    }

    @Override
    public void setState5_Payed() {
        orderState = OrderState.Stage5_Payed;
    }

    @Override
    public void addDish(Dish dish) {
        order.addDish(dish);
    }

    @Override
    public synchronized void increaseNumberOfCookedDishes() {
        if (orderState == OrderState.Stage3_Preparing) {
            numberOfCookedDishes++;
        }

        // Не потокобезопасно, но ничего не слечится, если несколько потоков обртятся в функцию
        if (isCooked()) {
            orderState = OrderState.Stage4_Ready;
        }
    }

    @Override
    public Boolean isCooked() {
        int dishesCounter = 0;
        for (Map.Entry<Dish, Integer> dishPosition : order.getDish().entrySet()) {
            dishesCounter += dishPosition.getValue();
        }

        return numberOfCookedDishes == dishesCounter;
    }

    @Override
    public Order getOrder() {
        return order;
    }


    @Override
    public OrderState getOrderState() {
        return orderState;
    }

    @Override
    public int getNumberOfCookedDishes() {
        return numberOfCookedDishes;
    }

    @Override
    public void setDefault() {
        order = new Order();
        numberOfCookedDishes = 0;
        orderState = OrderState.Stage1_NotExist;
    }

}
