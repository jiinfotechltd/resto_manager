package com.jiinfotech.restomanager.db.dish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    @Autowired
    private DishRepo dishRepo;

    public void addDish(Dish dish) {
        dishRepo.save(dish);
    }

    public List<Dish> getAllDishes() {
        return dishRepo.findAllByIsDeletedFalse();
    }

    public Optional<Dish> getDishById(long id) {
        return dishRepo.findById(id);
    }

    public void updateDish(Dish dishDetails) {
        Optional<Dish> dish = getDishById(dishDetails.getId());
        if (dish.isPresent()) {
            dish.get().setName(dishDetails.getName());
            dish.get().setPrice(dishDetails.getPrice());
            dish.get().setImage(dishDetails.getImage());
            dish.get().setCategory(dishDetails.getCategory());
            dish.get().setIsVegetarian(dishDetails.getIsVegetarian());
            dishRepo.save(dish.get());
        }
    }

    public void deleteDish(long id) {
        Optional<Dish> mayBeDish = dishRepo.findById(id);
        if(mayBeDish.isPresent()){
            mayBeDish.get().setDeleted(!mayBeDish.get().isDeleted());
            mayBeDish.get().setDeletedAt(Timestamp.from(Instant.now()));
            dishRepo.save(mayBeDish.get());
        }
    }

}
