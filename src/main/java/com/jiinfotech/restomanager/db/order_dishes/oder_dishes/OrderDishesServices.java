package com.jiinfotech.restomanager.db.order_dishes.oder_dishes;

import com.jiinfotech.restomanager.db.dish.Dish;
import com.jiinfotech.restomanager.db.dish.DishRepo;
import com.jiinfotech.restomanager.db.order.Order;
import com.jiinfotech.restomanager.db.order.OrderRepo;
import com.jiinfotech.restomanager.db.table.RestaurantTable;
import com.jiinfotech.restomanager.db.table.RestaurantTableRepo;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class OrderDishesServices {

    @Autowired
    private  DishRepo dishRepo;
    @Autowired
    private  OrderRepo orderRepo;
    @Autowired
    private  OrderDishesRepo orderDishesRepo;
    @Autowired
    private RestaurantTableRepo restaurantTableRepo;

    public void createNewOrder(Long tableId, Long dishId, int dishQun) {

        OrderDishes orderDishes = new OrderDishes();
        orderDishes.setDishId(dishId);
        orderDishes.setDishQuantity(dishQun);
        Optional<RestaurantTable> restaurantTable = restaurantTableRepo.findById(tableId);
        if(restaurantTable.isPresent()){
            restaurantTable.get().setIsVacant(false);
            restaurantTableRepo.save(restaurantTable.get());
        }

        Order order = new Order();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        order.setCreatedAt(currentTime);
        order.setIsPaid(false);
        order.setTableId(tableId);
        Optional<Dish> mayBeDish = dishRepo.findById(dishId);
        double orderamount = 0.0;
        if (mayBeDish.isPresent()) {
           orderamount += mayBeDish.get().getPrice();
        }
        order.setTotalAmount(orderamount);

        Order saved = orderRepo.save(order);
        orderDishes.setOrderId(saved.getId());
        orderDishesRepo.save(orderDishes);
    }


    public void updateOrder(Long tableId, Long[] dishIds) {

    }
}
