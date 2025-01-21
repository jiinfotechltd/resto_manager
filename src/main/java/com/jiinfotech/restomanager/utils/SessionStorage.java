package com.jiinfotech.restomanager.utils;

import com.jiinfotech.restomanager.db.dish.Dish;
import com.jiinfotech.restomanager.db.dish.DishRepo;
import com.jiinfotech.restomanager.db.order.Order;
import com.jiinfotech.restomanager.db.order_dishes.oder_dishes.OrderDishes;
import com.jiinfotech.restomanager.db.table.RestaurantTable;
import com.jiinfotech.restomanager.db.table.RestaurantTableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("session")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionStorage {

    @Autowired
    private RestaurantTableRepo restaurantTableRepo;
    @Autowired
    private GoogleChatNotificationService googleChatNotificationService;
    @Autowired
    private DishRepo dishRepo;

    private final HashMap<Order, List<OrderDishes>> orderAndDishes;
    private final HashMap<RestaurantTable, HashMap<Order, List<OrderDishes>>> tableAndThereOrder;

    public SessionStorage(HashMap<Order, List<OrderDishes>> orderAndDishes, HashMap<RestaurantTable, HashMap<Order, List<OrderDishes>>> tableAndThereOrder) {
        this.orderAndDishes = orderAndDishes;
        this.tableAndThereOrder = tableAndThereOrder;
    }

    public SessionStorage getSession() {
        return new SessionStorage(this.orderAndDishes, this.tableAndThereOrder);
    }

    public HashMap<Order, List<OrderDishes>> getOrderAndDishes() {
        return this.orderAndDishes;
    }

    public HashMap<RestaurantTable, HashMap<Order, List<OrderDishes>>> getTableAndThereOrder() {
        return this.tableAndThereOrder;
    }


    public void setSessionStorage(long tableId, long dishId, int quantity) {
        Optional<RestaurantTable> mayBeTable = restaurantTableRepo.findById(tableId);
        if (mayBeTable.isPresent()) {
            RestaurantTable table = mayBeTable.get();
            if (this.tableAndThereOrder.containsKey(table)) {
                HashMap<Order, List<OrderDishes>> orders = this.tableAndThereOrder.get(table);

                Order existingOrder = orders.keySet().iterator().next();
                double totalAmount = existingOrder.getTotalAmount();
                Optional<OrderDishes> existingDish = orders.get(existingOrder).stream()
                        .filter(orderDish -> orderDish.getDishId() == dishId)
                        .findFirst();
                if (existingDish.isPresent()) {
                    existingDish.get().setDishQuantity(existingDish.get().getDishQuantity() + quantity);
                    Dish dish = dishRepo.findById(existingDish.get().getDishId()).get(); // Update quantity
                    totalAmount += dish.getPrice() * quantity;
                    existingDish.get().setDishTotalAmount(dish.getPrice() * (existingDish.get().getDishQuantity()));
                    existingDish.get().setDishPrice(dish.getPrice());
                    existingDish.get().setDishName(dish.getName());
                } else {
                    OrderDishes newOrderDishes = new OrderDishes();
                    newOrderDishes.setDishId(dishId);
                    Dish dish = dishRepo.findById(dishId).get();
                    newOrderDishes.setDishName(dish.getName());
                    newOrderDishes.setDishPrice(dish.getPrice());
                    newOrderDishes.setDishQuantity(quantity);
                    totalAmount += dish.getPrice() * quantity;
                    newOrderDishes.setDishTotalAmount(dish.getPrice() * quantity);
                    orders.get(existingOrder).add(newOrderDishes);
                }
                existingOrder.setTotalAmount(totalAmount);

                // Update the session storage with the modified order list.
                tableAndThereOrder.put(table, orders);
            } else {
                // If no orders exist for the table, create a new order.
                Order newOrder = new Order();
                double totalAmount = newOrder.getTotalAmount();
                newOrder.setTableId(tableId);
                OrderDishes newOrderDishes = new OrderDishes();
                Dish dish = dishRepo.findById(dishId).get();
                totalAmount += dish.getPrice() * quantity;
                newOrderDishes.setDishId(dishId);
                newOrderDishes.setDishName(dish.getName());
                newOrderDishes.setDishPrice(dish.getPrice());
                newOrderDishes.setDishTotalAmount(dish.getPrice() * quantity);
                newOrder.setTotalAmount(totalAmount);
                newOrderDishes.setDishQuantity(quantity);
                List<OrderDishes> newOrderDishesList = new ArrayList<>();
                newOrderDishesList.add(newOrderDishes);
                HashMap<Order, List<OrderDishes>> newOrderMap = new HashMap<>();
                newOrderMap.put(newOrder, newOrderDishesList);
                tableAndThereOrder.put(table, newOrderMap);
            }
        } else {
            googleChatNotificationService.sendNotification("Unable to find table with id " + tableId + " while session creating");
        }
    }
}
