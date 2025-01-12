package com.jiinfotech.restomanager.db.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService {

    @Autowired
    private DishRepo dishRepo;

    public Dish addDish(Dish dish) {
        return dishRepo.save(dish);
    }

    public List<Dish> getAllDishes() {
        return dishRepo.findAll();
    }

    public Dish getDishById(int id) {
        return dishRepo.findById(id).orElse(null);
    }

    public Dish updateDish(int id, Dish dishDetails) {
        Dish dish = getDishById(id);
        if (dish != null) {
            dish.setName(dishDetails.getName());
            dish.setPrice(dishDetails.getPrice());
            return dishRepo.save(dish);
        }
        return null;
    }

    public boolean deleteDish(int id) {
        if (dishRepo.existsById(id)) {
            dishRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
