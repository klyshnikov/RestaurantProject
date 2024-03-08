package ru.hse.restaurant.project.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.exceptions.PriceLessThanZeroException;
import ru.hse.restaurant.project.exceptions.TimeToCookLessThanZeroException;

public interface AdminApi {
    ResponseEntity<String> addDishToMenu(Dish dish) throws TimeToCookLessThanZeroException, PriceLessThanZeroException;

    ResponseEntity<String> setName(String dishName, String newName);

    ResponseEntity<String> setTimeToCook(String dishName, int secondsToCook) throws TimeToCookLessThanZeroException;

    ResponseEntity<String> setPrise(String dishName, double prise) throws PriceLessThanZeroException;

    ResponseEntity<String> setDescription(String dishName, String description);

    ResponseEntity<String> deleteDish(String name);
}
