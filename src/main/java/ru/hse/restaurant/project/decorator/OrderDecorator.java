package ru.hse.restaurant.project.decorator;

import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.Order;

public interface OrderDecorator {

    public void addDish(Dish dish);
    public void makePayed();
    public Boolean isCooked();
    public Boolean isPayed();

    public Boolean isExist();
    public Order getOrder();
    public void increaseNumberOfCookedDishes();
    //public Boolean isCreated();
    public void setNotExisted();
    public void clearFields();

    public void setExisted();

}
