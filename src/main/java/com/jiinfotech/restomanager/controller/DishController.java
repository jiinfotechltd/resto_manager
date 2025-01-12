package com.jiinfotech.restomanager.controller;

import com.jiinfotech.restomanager.db.dish.Dish;
import com.jiinfotech.restomanager.db.dish.DishService;
import com.jiinfotech.restomanager.utils.Response;
import com.jiinfotech.restomanager.utils.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping(value = Routes.dishCreate)
    protected ResponseEntity<Response<Dish>> addDish(@RequestBody Dish dish) {

        if (dish.getName() == null || dish.getName().isEmpty()) {
            Response<Dish> response = new Response<>();
            response.setMessage("Dish name cannot be empty");
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Dish newDish = dishService.addDish(dish);
        Response<Dish> response = new Response<>();
        response.setMessage("Dish added successfully");
        response.setStatus(HttpStatus.OK.value());
        response.setData(newDish);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = Routes.getDishById)
    protected ResponseEntity<Response<Dish>> getDishById(@PathVariable("id") int id) {
        Dish dish = dishService.getDishById(id);
        Response<Dish> response = new Response<>();
        if (dish != null) {
            response.setMessage("Dish retrieved successfully");
            response.setStatus(HttpStatus.OK.value());
            response.setData(dish);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Dish not found");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = Routes.dishUpdate)
    protected ResponseEntity<Response<Dish>> updateDish(@PathVariable("id") int id, @RequestBody Dish dishDetails) {
        Dish updatedDish = dishService.updateDish(id, dishDetails);
        Response<Dish> response = new Response<>();
        if (updatedDish != null) {
            response.setMessage("Dish updated successfully");
            response.setStatus(HttpStatus.OK.value());
            response.setData(updatedDish);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Dish not found for update");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = Routes.dishDelete)
    protected ResponseEntity<Response<String>> deleteDish(@PathVariable int id) {
        boolean isDeleted = dishService.deleteDish(id); // Service method now returns boolean
        Response<String> response = new Response<>();

        if (isDeleted) {
            response.setMessage("Dish deleted successfully");
            response.setStatus(HttpStatus.OK.value());
            response.setData("Dish with ID " + id + " has been deleted.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Dish not found for deletion");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setData("Failed to delete dish with ID " + id);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
