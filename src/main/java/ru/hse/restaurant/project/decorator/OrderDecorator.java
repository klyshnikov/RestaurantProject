package ru.hse.restaurant.project.decorator;

import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.Order;

public interface OrderDecorator {

    void setState1_NotExist();
    void setState2_Creating();
    void setState3_Preparing();
    void setState4_Ready();
    void setState5_Payed();

    OrderState getOrderState();
    Order getOrder();

    void addDish(Dish dish);
    Boolean isCooked();

    void increaseNumberOfCookedDishes();
    int getNumberOfCookedDishes();

    void setDefault();

}
