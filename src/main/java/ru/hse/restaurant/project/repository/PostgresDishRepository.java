package ru.hse.restaurant.project.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.hse.restaurant.project.entity.Dish;
import ru.hse.restaurant.project.exceptions.PriceLessThanZeroException;
import ru.hse.restaurant.project.exceptions.TimeToCookLessThanZeroException;

import java.util.List;
import java.util.UUID;

//@Repository
@AllArgsConstructor
public class PostgresDishRepository {
    public static final RowMapper<Dish> DISH_ROW_MAPPER =
            (rs, i) -> {
                Dish dish = new Dish();

                //dish.setId(rs.getInt("id"));
                dish.setName(rs.getString("name"));
                try {
                    dish.setPrice(rs.getDouble("price"));
                } catch (PriceLessThanZeroException e) {
                    throw new RuntimeException(e);
                }
                try {
                    dish.setTimeToCook(rs.getInt("timeToCook"));
                } catch (TimeToCookLessThanZeroException e) {
                    throw new RuntimeException(e);
                }

                dish.setDescription(rs.getString("description"));

                return dish;
            };

    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();

//    public List<Dish> getAllDish() {
//
//    }
}
