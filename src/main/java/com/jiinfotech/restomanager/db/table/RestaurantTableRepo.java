package com.jiinfotech.restomanager.db.table;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantTableRepo extends JpaRepository<RestaurantTable, Long> {

    Optional<RestaurantTable> findByTableNumber(int tableNumber);
}
