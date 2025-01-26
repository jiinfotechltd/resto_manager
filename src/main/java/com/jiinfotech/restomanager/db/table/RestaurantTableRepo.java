package com.jiinfotech.restomanager.db.table;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantTableRepo extends JpaRepository<RestaurantTable, Long> {

    Optional<RestaurantTable> findByTableNumber(int tableNumber);
}
