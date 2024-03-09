package ru.hse.restaurant.project.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> addDishToMenu(@RequestBody Dish dish) throws TimeToCookLessThanZeroException, PriceLessThanZeroException {
        try {
            dishRepository.addDish(dish);
            return ResponseEntity.ok("Добавлено!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @PutMapping("/set-name/{dishName}/{newName}")
    public ResponseEntity<String> setName(@PathVariable String dishName, @PathVariable String newName) {
        try {
            Dish dish = dishRepository.getDish(dishName);
            dish.setName(newName);
            dishRepository.deleteDish(dishName);
            dishRepository.addDish(dish);
            return ResponseEntity.ok("Имя установлено!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @PutMapping("/set-time-to-cook/{dishName}/{secondsToCook}")
    public ResponseEntity<String> setTimeToCook(@PathVariable String dishName, @PathVariable int secondsToCook) throws TimeToCookLessThanZeroException {
        try {
            Dish dish = dishRepository.getDish(dishName);
            dish.setTimeToCook(secondsToCook);
            dishRepository.addDish(dish);
            return ResponseEntity.ok("Время установлено!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @PutMapping("/set-prise/{dishName}/{prise}")
    public ResponseEntity<String> setPrise(@PathVariable String dishName, @PathVariable double prise) throws PriceLessThanZeroException {
        try {
            Dish dish = dishRepository.getDish(dishName);
            dish.setPrice(prise);
            dishRepository.addDish(dish);
            return ResponseEntity.ok("Цена установлена!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @PutMapping("/set-description/{dishName}")
    public ResponseEntity<String> setDescription(@PathVariable String dishName, @RequestBody String description) {
        try {
            Dish dish = dishRepository.getDish(dishName);
            dish.setDescription(description);
            dishRepository.addDish(dish);
            return ResponseEntity.ok("Описание установлено!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @PutMapping("/delete-dish")
    public ResponseEntity<String> deleteDish(String name) {
        try {
            dishRepository.deleteDish(name);
            return ResponseEntity.ok("Успешно удалено!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @GetMapping("/get-menu")
    public List<Dish> getMenu() {
        return dishRepository.getAllDishes();
    }
}
