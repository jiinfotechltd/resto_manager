package com.jiinfotech.restomanager.controller;

import com.jiinfotech.restomanager.db.dish.Dish;
import com.jiinfotech.restomanager.db.dish.DishService;
import com.jiinfotech.restomanager.utils.BaseServices;
import com.jiinfotech.restomanager.utils.Response;
import com.jiinfotech.restomanager.utils.Routes;
import com.jiinfotech.restomanager.utils.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class DishController extends BaseServices {

    @Autowired
    private DishService dishService;

    @GetMapping(Routes.dish)
    public String getDishPage(Model model){
        List<Dish> allDishes = dishService.getAllDishes();
        model.addAttribute("allDishes", allDishes);
        model.addAttribute("dish", new Dish());
        return Templates.dishes;
    }

    @PostMapping(value = Routes.dishCreate)
    protected String addDish(@ModelAttribute Dish dish) {
        dishService.addDish(dish);
        return  redirect(Routes.dish);
    }

    @GetMapping(value = Routes.getDishById)
    protected ResponseEntity<Response<Dish>> getDishById(@PathVariable("id") int id) {
        Optional<Dish> dish = dishService.getDishById(id);
        Response<Dish> response = new Response<>();
        if (dish.isPresent()) {
            response.setMessage("Dish retrieved successfully");
            response.setStatus(HttpStatus.OK.value());
            response.setData(dish.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Dish not found");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = Routes.dishUpdate)
    protected String updateDish(@ModelAttribute Dish dishDetails, @RequestParam(value = "vegetarian", required = false) Boolean vegetarian) {
        dishDetails.setIsVegetarian(vegetarian);
        dishService.updateDish(dishDetails);
         return redirect(Routes.dish);
    }

    @PostMapping(value = Routes.dishDelete)
    protected String deleteDish(@RequestParam(value = "id") Long id) {
         dishService.deleteDish(id);
         return redirect(Routes.dish);
    }

}