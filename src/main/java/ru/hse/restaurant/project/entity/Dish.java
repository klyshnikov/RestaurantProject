package ru.hse.restaurant.project.entity;
import lombok.Data;
import ru.hse.restaurant.project.exceptions.PriceLessThanZeroException;
import ru.hse.restaurant.project.exceptions.TimeToCookLessThanZeroException;

import java.time.Duration;

@Data
public class Dish {
    private int id;
    private String name;
    private double price;
    private int timeToCook;
    private String description;

    public Dish() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public double getPrice() {
        return price;
    }

    public void setPrice(double value) throws PriceLessThanZeroException {
        if (value <= 0) throw new PriceLessThanZeroException("Price can't be less than 0");
        price = value;
    }

    public int getTimeToCook() {
        return timeToCook;
    }

    public void setTimeToCook(int value) throws TimeToCookLessThanZeroException {
        if ( value <= 0 ) throw new TimeToCookLessThanZeroException("Cooking time can't be less than 0");
        timeToCook = value;
    }

    public String getDescription() { return description; }

    public void setDescription(String value) {description = value;}

    public Dish(String name, double prise, int timeToCook, String description) throws PriceLessThanZeroException, TimeToCookLessThanZeroException {
        setName(name);
        setPrice(prise);
        setTimeToCook(timeToCook);
        setDescription(description);
    }
}
