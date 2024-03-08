package ru.hse.restaurant.project.api;

import org.springframework.http.ResponseEntity;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.Order;
import ru.hse.restaurant.project.exceptions.OrderIsNotAlreadyCookedException;
import ru.hse.restaurant.project.exceptions.OrderIsNotCreatedYetException;
import ru.hse.restaurant.project.exceptions.OrderIsNotPayedException;

import java.util.List;

public interface ClientApi {
    public ResponseEntity<List<Dish>> getMenu();

    public ResponseEntity<String> createOrder();

    public ResponseEntity<String> addInOrder(String dishName);

    public ResponseEntity<String> addInOrder(String dishName, int amount);

    public ResponseEntity<String> prepareOrder() throws InterruptedException;

    public ResponseEntity<String> payForOrder() throws OrderIsNotAlreadyCookedException;

    public ResponseEntity<String> getOrder() throws OrderIsNotAlreadyCookedException, OrderIsNotPayedException, OrderIsNotCreatedYetException;

    public ResponseEntity<String> canselOrder();

    public ResponseEntity<String> getOrderInfo();
}
