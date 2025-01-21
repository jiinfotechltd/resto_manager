package com.jiinfotech.restomanager.db.order_dishes.oder_dishes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDishesRepo extends JpaRepository<OrderDishes, Long> {

}
