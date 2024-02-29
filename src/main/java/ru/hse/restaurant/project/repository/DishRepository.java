package ru.hse.restaurant.project.repository;

import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.entity.Review;

import java.util.List;

public interface DishRepository {
    public List<Dish> getAllDishes();
    public List<Review> getAllReview();
    public List<Review> getReviewByDish(Dish dish);
    public Dish getDish(String name);
    public void addReview(Review review);
    public void addDish(Dish dish);
    public void deleteDish(String name);
}
