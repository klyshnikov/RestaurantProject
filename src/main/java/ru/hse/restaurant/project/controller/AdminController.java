package ru.hse.restaurant.project.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hse.restaurant.project.api.AdminApi;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.User;
import ru.hse.restaurant.project.exceptions.PriceLessThanZeroException;
import ru.hse.restaurant.project.exceptions.TimeToCookLessThanZeroException;
import ru.hse.restaurant.project.repository.DishRepository;
import ru.hse.restaurant.project.service.AuthService;

import java.io.IOException;
import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController implements AdminApi {

    private final DishRepository dishRepository;
    private final AuthService authService;

    // Auth

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) throws IOException {
        if (authService.register(user)) {
            return ResponseEntity.ok("Успешно зарегистрированы");
        }

        return ResponseEntity.badRequest().body("Регистрация не удалась. Возможно, данный пользователь уже существует");
    }

    @PostMapping("/login/{login}/{password}")
    public ResponseEntity<String> login(@PathVariable String login, @PathVariable String password) throws IOException {
        if (authService.login(login, password)) {
            return ResponseEntity.ok("Вы успешно вошли");
        }

        return ResponseEntity.badRequest().body("Неуспешная попытка входа");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Вы вышли");
    }

    @Override
    @PostMapping("/add-dish-to-menu")
    public ResponseEntity<String> addDishToMenu(@RequestBody Dish dish) throws TimeToCookLessThanZeroException, PriceLessThanZeroException {
        if (!authService.isEnterAsAdmin()) {
            return ResponseEntity.badRequest().body("Вы не вошли как админ");
        }

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
        if (!authService.isEnterAsAdmin()) {
            return ResponseEntity.badRequest().body("Вы не вошли как админ");
        }

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
        if (!authService.isEnterAsAdmin()) {
            return ResponseEntity.badRequest().body("Вы не вошли как админ");
        }

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
        if (!authService.isEnterAsAdmin()) {
            return ResponseEntity.badRequest().body("Вы не вошли как админ");
        }

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
        if (!authService.isEnterAsAdmin()) {
            return ResponseEntity.badRequest().body("Вы не вошли как админ");
        }

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
        if (!authService.isEnterAsAdmin()) {
            return ResponseEntity.badRequest().body("Вы не вошли как админ");
        }

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
