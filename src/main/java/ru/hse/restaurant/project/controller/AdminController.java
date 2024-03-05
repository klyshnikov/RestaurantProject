package ru.hse.restaurant.project.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hse.restaurant.project.api.AdminApi;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.exceptions.PriceLessThanZeroException;
import ru.hse.restaurant.project.exceptions.TimeToCookLessThanZeroException;
import ru.hse.restaurant.project.repository.DishRepository;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController implements AdminApi {

    private final DishRepository dishRepository;

    @Override
    @PostMapping("/add-dish-to-menu")
    public void addDishToMenu(@RequestBody Dish dish) throws TimeToCookLessThanZeroException, PriceLessThanZeroException {
        dishRepository.addDish(dish);
    }

    @Override
    @PutMapping("set-name/{dish-name}/{new-name}")
    public void setName(@PathVariable String dishName, @PathVariable String newName) {
        Dish dish = dishRepository.getDish(dishName);
        dish.setName(newName);
        dishRepository.deleteDish(dishName);
        dishRepository.addDish(dish);
    }

    @Override
    @PutMapping("/setTimeToCook/{dish-name}/{seconds-to-cook}")
    public void setTimeToCook(@PathVariable String dishName, @PathVariable int secondsToCook) throws TimeToCookLessThanZeroException {
        Dish dish = dishRepository.getDish(dishName);
        //Duration cookDuration = Duration.ofSeconds(secondsToCook);
        dish.setTimeToCook(secondsToCook);
        dishRepository.addDish(dish);
    }

    @Override
    @PutMapping("/set-prise/{dish-name}/{prise}")
    public void setPrise(@PathVariable String dishName, @PathVariable double prise) throws PriceLessThanZeroException {
        Dish dish = dishRepository.getDish(dishName);
        dish.setPrice(prise);
        dishRepository.addDish(dish);
    }

    @Override
    @PutMapping("/set-description/{dish-name}")
    public void setDescription(@PathVariable String dishName, @RequestBody String description) {
        Dish dish = dishRepository.getDish(dishName);
        dish.setDescription(description);
        dishRepository.addDish(dish);
    }

    @Override
    @PutMapping("/delete-dish")
    public void deleteDish(String name) {
        dishRepository.deleteDish(name);
    }

    @GetMapping("/get-menu")
    public List<Dish> getMenu() {
        return dishRepository.getAllDishes();
    }
}
