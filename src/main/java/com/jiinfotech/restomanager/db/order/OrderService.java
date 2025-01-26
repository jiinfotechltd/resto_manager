package com.jiinfotech.restomanager.db.order;

import com.jiinfotech.restomanager.db.dish.DishRepo;
import com.jiinfotech.restomanager.db.table.RestaurantTableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RestaurantTableRepo tablesRepo;
    @Autowired
    private DishRepo dishRepo;

    public List<Order> getAllOrderForTable(){
        return orderRepo.findAll();
    }

}