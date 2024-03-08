package ru.hse.restaurant.project.entity;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

public class Order {
    private Map<Dish, Integer> dishes;

    public Map<Dish, Integer> getDish() {
        return dishes;
    }

    //private Boolean isCooked = false;
    //public void makeCooked() {isCooked = true;}

    public Order() {
        dishes = new HashMap<Dish, Integer>();
    }

    public void addDish(Dish dish) {
        addDish(dish, 1);
    }

    public void addDish(Dish dish, int amount) {
        if (dishes.containsKey(dish)) {
            var currentAmount = dishes.get(dish);
            dishes.put(dish, currentAmount+amount);
        } else {
            dishes.put(dish, amount);
        }
    }
}
