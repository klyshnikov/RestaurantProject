package ru.hse.restaurant.project.repository;

import org.springframework.stereotype.Component;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.Review;
import ru.hse.restaurant.project.exceptions.DishIsNotFoundException;
import ru.hse.restaurant.project.exceptions.PriceLessThanZeroException;
import ru.hse.restaurant.project.exceptions.TimeToCookLessThanZeroException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class SimpleDishRepository implements DishRepository {
    ArrayList<Dish> dishes = new ArrayList<Dish>();


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

        if (finded == null) {
            throw new DishIsNotFoundException();
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

        if (dish.getPrice() <= 0) {
            throw new PriceLessThanZeroException();
        }

        if (dish.getTimeToCook() <= 0) {
            throw new TimeToCookLessThanZeroException();
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
