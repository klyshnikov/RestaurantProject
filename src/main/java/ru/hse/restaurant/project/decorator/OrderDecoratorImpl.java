package ru.hse.restaurant.project.decorator;

import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.Order;

import java.util.Map;

public class OrderDecoratorImpl implements OrderDecorator {
    private final Order order;
    private Boolean payed = false;
    private int numberOfCookedDishes = 0;
    private OrderState orderState = OrderState.Stage1_NotExist;

    public OrderDecoratorImpl() {
        order = new Order();
    }

    @Override
    public void addDish(Dish dish) {
        order.addDish(dish);
    }

    @Override
    public void makePayed() {
        payed = true;
    }

    @Override
    public synchronized void increaseNumberOfCookedDishes() {
        numberOfCookedDishes++;
    }

//    @Override
//    public Boolean isPrepared() {
//        return orderState != OrderState.Stage1_NotExist && orderState != OrderState.Stage2_Creating;
//    }

    @Override
    public void setNotExisted() {
        orderState = OrderState.Stage1_NotExist;
    }

    @Override
    public void clearFields() {
        numberOfCookedDishes = 0;
        // Вообще бесполезная вещь
    }

    @Override
    public void setExisted() {
        orderState = OrderState.Stage2_Creating;
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
    public Boolean isPayed() {
        return payed;
    }

    @Override
    public Boolean isExist() {
        return orderState != OrderState.Stage1_NotExist;
    }

    @Override
    public Order getOrder() {
        return order;
    }

}
