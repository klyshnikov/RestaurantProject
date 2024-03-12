package ru.hse.restaurant.project.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    private final AuthService authService = new AuthService();

    // Auth

    @PostMapping("/register")
    @Operation(summary = "Регистрация")
    public ResponseEntity<String> register(@RequestBody User user) throws IOException {
        try {
            if (authService.register(user)) {
                return ResponseEntity.ok("Успешно зарегистрированы");
            }

            return ResponseEntity.badRequest().body("Регистрация не удалась. Возможно, данный пользователь уже существует");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }

    }

    @PostMapping("/login/{login}/{password}")
    @Operation(summary = "Вход по логину и паролю")
    public ResponseEntity<String> login(@PathVariable String login, @PathVariable String password) throws IOException {
        if (authService.login(login, password)) {
            return ResponseEntity.ok("Вы успешно вошли");
        }

        return ResponseEntity.badRequest().body("Неуспешная попытка входа");
    }

    @PostMapping("/logout")
    @Operation(summary = "Выход")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Вы вышли");
    }

    @Override
    @PostMapping("/add-dish-to-menu")
    @Operation(summary = "Добавление нового блюда в меню.")
    public ResponseEntity<String> addDishToMenu(@RequestBody Dish dish) throws TimeToCookLessThanZeroException, PriceLessThanZeroException {
        if (!authService.isEnterAsAdmin()) {
            return ResponseEntity.badRequest().body("Вы не вошли как админ");
        }

        try {
            dishRepository.addDish(dish);
            return ResponseEntity.ok("Добавлено!");
        } catch (Exception e) {
            System.out.println("here");
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @PutMapping("/set-name/{dishName}/{newName}")
    @Operation(summary = "Изменение названия названия блюда. Первый {dishName} - старое имя, {newName} - новое")
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
    @Operation(summary = "Установка времени приготовления заказа. {dishName} - название блюда, {secondsToCook} - кол-во секунд готовки")
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
    @Operation(summary = "Изменение цены заказа . {dishName} - название блюда, {price} - цена")
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
    @Operation(summary = "Изменение описания заказа. {dishName} - название блюда, {description} - описание")
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
    @Operation(summary = "Удаление заказа.")
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
    @Operation(summary = "Получение меню")
    public List<Dish> getMenu() {
        return dishRepository.getAllDishes();
    }
}
