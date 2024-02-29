package ru.hse.restaurant.project.repository;

import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.Review;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SimpleDishRepository implements DishRepository {
    ArrayList<Dish> dishes;

    public SimpleDishRepository() {
        dishes = new ArrayList<Dish>();
    }

    @Override
    public List<Dish> getAllDishes() {
        return dishes;
    }

    @Override
    public List<Review> getAllReview() {
        return null;
    }

    @Override
    public List<Review> getReviewByDish(Dish dish) {
        return null;
    }

    @Override
    public Dish getDish(String name) {
        Dish finded = null;
        for (Dish dish : dishes) {
            if (Objects.equals(dish.getName(), name)) {
                finded = dish;
            }
        }

        return finded;
    }

    @Override
    public void addReview(Review review) {

    }

    @Override
    public void addDish(Dish dish) {
        for (int i = 0; i < dishes.size(); ++i) {
            if (Objects.equals(dishes.get(i).getName(), dish.getName())) {
                dishes.set(i, dish);
                return;
            }
        }

        dishes.add(dish);
    }

    @Override
    public void deleteDish(String name) {
        Dish dish = getDish(name);

        for (int i = 0; i < dishes.size(); ++i) {
            if (Objects.equals(dishes.get(i).getName(), dish.getName())) {
                dishes.remove(i);
                return;
            }
        }
    }
}
