package ru.hse.restaurant.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hse.restaurant.project.api.ClientApi;
import ru.hse.restaurant.project.command.OrderInvoker;
import ru.hse.restaurant.project.decorator.OrderDecorator;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.User;
import ru.hse.restaurant.project.exceptions.OrderIsNotAlreadyCookedException;
import ru.hse.restaurant.project.exceptions.OrderIsNotCreatedYetException;
import ru.hse.restaurant.project.exceptions.OrderIsNotPayedException;
import org.springframework.http.ResponseEntity;
import ru.hse.restaurant.project.service.AuthService;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController implements ClientApi {
    private final OrderInvoker orderInvoker;
    private final OrderDecorator orderDecorator;
    private final AuthService authService;

    // Auth

    @PostMapping("/register")
    @Operation(summary = "Регистрация пользователя")
    public ResponseEntity<String> register(@RequestBody User user) throws IOException {
        System.out.println("111");
        if (authService.register(user)) {
            return ResponseEntity.ok("Успешно зарегистрированы");
        }

        return ResponseEntity.badRequest().body("Регистрация не удалась. Возможно, данный пользователь уже существует");
    }

    @PostMapping("/login/{login}/{password}")
    @Operation(summary = "Вход по логину и паролю")
    public ResponseEntity<String> login(@PathVariable String login, @PathVariable String password) throws IOException {
        System.out.println("111");
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
    @GetMapping("/get-menu")
    @Operation(summary = "Получить блюда из меню")
    public ResponseEntity<List<Dish>> getMenu() {
        return ResponseEntity.ok(orderInvoker.getActualMenu());
    }

    @Override
    @PostMapping("/create-order")
    @Operation(summary = "Создание нового заказа")
    public ResponseEntity<String> createOrder() {
        if (!authService.isEnterAsUser()) {
            return ResponseEntity.badRequest().body("Вы не вошли как пользователь");
        }

        try {
            orderInvoker.createOrder(orderDecorator);
            return ResponseEntity.ok("Заказ успешно создан!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/add-in-order/{dishName}")
    @Operation(summary = "Добавление блюда из меню в заказ. {dishName} - название блюда")
    public ResponseEntity<String> addInOrder(@PathVariable String dishName) {
        if (!authService.isEnterAsUser()) {
            return ResponseEntity.badRequest().body("Вы не вошли как пользователь");
        }

        try {
            orderInvoker.addDish(dishName, orderDecorator);
            return ResponseEntity.ok("Блюдо успешно добавлено в меню");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @PutMapping("/add-in-order/{dishName}/{amount}")
    @Operation(summary = "Добавление блюда из меню в заказ. {dishName} - название блюда, {amount} - кол-во блюд")
    public ResponseEntity<String> addInOrder(@PathVariable String dishName, @PathVariable int amount) {
        if (!authService.isEnterAsUser()) {
            return ResponseEntity.badRequest().body("Вы не вошли как пользователь");
        }

        try {
            for (int i = 0; i<amount; ++i) {
                orderInvoker.addDish(dishName, orderDecorator);
            }
            return ResponseEntity.ok("Добавлено в заказ");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @PostMapping("/prepare-order")
    @Operation(summary = "Отправить блюдо готовиться на кухню")
    public ResponseEntity<String> prepareOrder() throws InterruptedException {
        if (!authService.isEnterAsUser()) {
            return ResponseEntity.badRequest().body("Вы не вошли как пользователь");
        }

        try {
            orderInvoker.prepare(orderDecorator);
            return ResponseEntity.ok("Готовится. Ожидайте.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }


    @Override
    @PostMapping("/pay-for-order")
    @Operation(summary = "Заплатить за заказ")
    public ResponseEntity<String> payForOrder() throws OrderIsNotAlreadyCookedException {
        if (!authService.isEnterAsUser()) {
            return ResponseEntity.badRequest().body("Вы не вошли как пользователь");
        }

        try {
            orderInvoker.pay(orderDecorator);
            return ResponseEntity.ok("Успешно оплачено!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @GetMapping("/get-order")
    @Operation(summary = "Получить приготовленный и оплаченный заказ")
    public ResponseEntity<String> getOrder() throws OrderIsNotAlreadyCookedException, OrderIsNotPayedException, OrderIsNotCreatedYetException {
        if (!authService.isEnterAsUser()) {
            return ResponseEntity.badRequest().body("Вы не вошли как пользователь");
        }

        try {
            return ResponseEntity.ok(orderInvoker.getOrder(orderDecorator).toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @PostMapping("/cansel-order")
    @Operation(summary = "Отменить заказ")
    public ResponseEntity<String> canselOrder() {
        if (!authService.isEnterAsUser()) {
            return ResponseEntity.badRequest().body("Вы не вошли как пользователь");
        }

        try {
            orderInvoker.cansel(orderDecorator);
            return ResponseEntity.ok("Отменено.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @GetMapping("/get-order-info")
    @Operation(summary = "Получить информацию о вашем заказе")
    public ResponseEntity<String> getOrderInfo() {
        if (!authService.isEnterAsUser()) {
            return ResponseEntity.badRequest().body("Вы не вошли как пользователь");
        }

        return ResponseEntity.ok(orderInvoker.getOrderInfo(orderDecorator));
    }
}
