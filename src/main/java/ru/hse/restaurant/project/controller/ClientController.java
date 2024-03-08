package ru.hse.restaurant.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hse.restaurant.project.api.ClientApi;
import ru.hse.restaurant.project.command.OrderInvoker;
import ru.hse.restaurant.project.decorator.OrderDecorator;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.Order;
import ru.hse.restaurant.project.exceptions.OrderIsNotAlreadyCookedException;
import ru.hse.restaurant.project.exceptions.OrderIsNotCreatedYetException;
import ru.hse.restaurant.project.exceptions.OrderIsNotPayedException;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController implements ClientApi {
    private final OrderInvoker orderInvoker;
    private final OrderDecorator orderDecorator;

    @Override
    @GetMapping
    public ResponseEntity<List<Dish>> getMenu() {
        return ResponseEntity.ok(orderInvoker.getActualMenu());
    }

    @Override
    @Operation(summary = "Создание заказа")
    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder() {
        orderInvoker.createOrder(orderDecorator);
        return ResponseEntity.ok("Заказ успешно создан!");
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/add-in-order/{dish-name}")
    public ResponseEntity<String> addInOrder(@PathVariable String dishName) {
        try {
            orderInvoker.addDish(dishName, orderDecorator);
            return ResponseEntity.ok("Блюдо успешно добавлено в меню");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Неизвестная ошибка!");
        }
    }

    @Override
    @PutMapping("/add-in-order/{dish-name}/{amount}")
    public ResponseEntity<String> addInOrder(@PathVariable String dishName, @PathVariable int amount) {
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
    public ResponseEntity<String> prepareOrder() throws InterruptedException {
        try {
            orderInvoker.prepare(orderDecorator);
            return ResponseEntity.ok("Готовится. Ожидайте.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }


    @Override
    @PostMapping("/pay-for-order")
    public ResponseEntity<String> payForOrder() throws OrderIsNotAlreadyCookedException {
        try {
            orderInvoker.pay(orderDecorator);
            return ResponseEntity.ok("Успешно оплачено!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @GetMapping("/get-order")
    public ResponseEntity<String> getOrder() throws OrderIsNotAlreadyCookedException, OrderIsNotPayedException, OrderIsNotCreatedYetException {
        try {
            return ResponseEntity.ok(orderInvoker.getOrder(orderDecorator).toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @PostMapping("/cansel-order")
    public ResponseEntity<String> canselOrder() {
        try {
            orderInvoker.cansel(orderDecorator);
            return ResponseEntity.ok("Отменено.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.toString());
        }
    }

    @Override
    @GetMapping("/get-order-info")
    public ResponseEntity<String> getOrderInfo() {
        return ResponseEntity.ok(orderInvoker.getOrderInfo(orderDecorator));
    }
}
