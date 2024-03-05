package ru.hse.restaurant.project.api;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.exceptions.PriceLessThanZeroException;
import ru.hse.restaurant.project.exceptions.TimeToCookLessThanZeroException;

public interface AdminApi {
    public void addDishToMenu(Dish dish) throws TimeToCookLessThanZeroException, PriceLessThanZeroException;

    void setName(String dishName, String newName);

    public void setTimeToCook(String dishName, int secondsToCook) throws TimeToCookLessThanZeroException;

    public void setPrise(String dishName, double prise) throws PriceLessThanZeroException;

    void setDescription(String dishName, String description);

    public void deleteDish(String name);
}
