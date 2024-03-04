package ru.hse.restaurant.project.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.hse.restaurant.project.api.ClientApi;
import ru.hse.restaurant.project.command.OrderInvoker;
import ru.hse.restaurant.project.command.Waiter;
import ru.hse.restaurant.project.decorator.OrderDecorator;
import ru.hse.restaurant.project.decorator.OrderDecoratorImpl;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.Order;
import ru.hse.restaurant.project.exceptions.OrderIsNotAlreadyCookedException;
import ru.hse.restaurant.project.exceptions.OrderIsNotCreatedYetException;
import ru.hse.restaurant.project.exceptions.OrderIsNotPayedException;
import ru.hse.restaurant.project.repository.SimpleDishRepository;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/client")
public class ClientController implements ClientApi {
    private final OrderInvoker orderInvoker;
    private final OrderDecorator orderDecorator;

//    public ClientController(IOrderInvoker orderInvoker, IOrderDecorator orderDecorator) {
//        this.orderInvoker = orderInvoker;
//        this.orderDecorator = orderDecorator;
//    }

    @Override
    @GetMapping
    public List<Dish> getMenu() {
        return orderInvoker.getActualMenu();
    }

    @Override
    @Operation(summary = "Создание заказа")
    @PostMapping("/create-order")
    public void createOrder() {
        orderInvoker.createOrder(orderDecorator);
    }

    @Override
    @PutMapping("/addInOrder/{dishName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addInOrder(@PathVariable String dishName) {
        orderInvoker.addDish(dishName, orderDecorator);
    }

    @Override
    @PutMapping("/addInOrder/{dishName}/{amount}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addInOrder(@PathVariable String dishName, @PathVariable int amount) {
        for (int i = 0; i<amount; ++i) {
            orderInvoker.addDish(dishName, orderDecorator);
        }
    }

    @Override
    @PostMapping("/prepareOrder")
    public void prepareOrder() throws InterruptedException {
        orderInvoker.prepare(orderDecorator);
    }


    @Override
    @PostMapping("/payForOrder")
    public void payForOrder() {
        orderInvoker.pay(orderDecorator);
    }

    @Override
    @GetMapping("/getOrder")
    public Order getOrder() throws OrderIsNotAlreadyCookedException, OrderIsNotPayedException, OrderIsNotCreatedYetException {
        return orderInvoker.getOrder(orderDecorator);
    }

    @Override
    @PostMapping("/canselOrder")
    public void canselOrder() {
        orderInvoker.cansel(orderDecorator);
    }
}
