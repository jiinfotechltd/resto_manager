package com.jiinfotech.restomanager.db.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Dish getDishById(long id) {
        return dishRepo.findById(id).orElse(null);
    }

    public Optional<Dish> updateDish(long id, Dish dishDetails) {
        Optional<Dish> dish = dishRepo.findById(id);
        if (dish.isPresent()) {
            dish.get().setName(dishDetails.getName());
            dish.get().setPrice(dishDetails.getPrice());
            return Optional.of(dishRepo.save(dish.get()));
        }
        return dish;
    }

    public boolean deleteDish(long id) {
        if (dishRepo.existsById(id)) {
            dishRepo.deleteById(id);
            return true;
        }
        return false;
    }

}
