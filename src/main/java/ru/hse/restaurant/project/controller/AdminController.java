package ru.hse.restaurant.project.controller;

import org.springframework.web.bind.annotation.*;
import ru.hse.restaurant.project.api.AdminApi;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.exceptions.PriceLessThanZeroException;
import ru.hse.restaurant.project.exceptions.TimeToCookLessThanZeroException;
import ru.hse.restaurant.project.repository.DishRepository;
import ru.hse.restaurant.project.repository.SimpleDishRepository;


@RestController
@RequestMapping("/api/v1/admin")
public class AdminController implements AdminApi {

    DishRepository dishRepository = new SimpleDishRepository();

    @Override
    @PostMapping("/addDishToMenu")
    public void addDishToMenu(@RequestBody Dish dish) throws TimeToCookLessThanZeroException, PriceLessThanZeroException {
        dishRepository.addDish(dish);
    }


    @Override
    @PutMapping("/changeDish")
    public void changeDish(@RequestBody Dish oldDish, @RequestBody Dish newDish) throws TimeToCookLessThanZeroException, PriceLessThanZeroException {
        dishRepository.addDish(newDish);
    }

    @Override
    @PutMapping("/setTimeToCook/{dishName}/{secondsToCook}")
    public void setTimeToCook(@PathVariable String dishName, @PathVariable int secondsToCook) throws TimeToCookLessThanZeroException {
        Dish dish = dishRepository.getDish(dishName);
        //Duration cookDuration = Duration.ofSeconds(secondsToCook);
        dish.setTimeToCook(secondsToCook);
        dishRepository.addDish(dish);
    }

    @Override
    @PutMapping("/setTimeToCook/{dishName}/{prise}")
    public void setPrise(@PathVariable String dishName, @PathVariable double prise) throws PriceLessThanZeroException {
        Dish dish = dishRepository.getDish(dishName);
        dish.setPrice(prise);
        dishRepository.addDish(dish);
    }

    @Override
    public void deleteDish(String name) {
        dishRepository.deleteDish(name);
    }
}
