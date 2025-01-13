package com.jiinfotech.restomanager.db.dish;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepo extends JpaRepository<Dish, Long> {

    List<Dish> findAllByIsDeletedFalse();
}

