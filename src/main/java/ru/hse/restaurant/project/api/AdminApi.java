package ru.hse.restaurant.project.api;

import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.exceptions.PriceLessThanZeroException;
import ru.hse.restaurant.project.exceptions.TimeToCookLessThanZeroException;

public interface AdminApi {
    public void addDishToMenu(Dish dish) throws TimeToCookLessThanZeroException, PriceLessThanZeroException;

    public void changeDish(Dish oldDish, Dish newDish) throws TimeToCookLessThanZeroException, PriceLessThanZeroException;

    public void setTimeToCook(String dishName, int secondsToCook) throws TimeToCookLessThanZeroException;

    public void setPrise(String dishName, double prise) throws PriceLessThanZeroException;

    public void deleteDish(String name);
}
