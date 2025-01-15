package com.jiinfotech.restomanager.db.order;

import com.jiinfotech.restomanager.db.dish.Dish;
import com.jiinfotech.restomanager.db.dish.DishRepo;
import com.jiinfotech.restomanager.db.table.RestaurantTable;
import com.jiinfotech.restomanager.db.table.RestaurantTableRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private RestaurantTableRepo tablesRepo;
    @Autowired
    private DishRepo dishRepo;

    public Order addOrder(Order order) {

        RestaurantTable table = tablesRepo.findById(order.getTable().getId())
                .orElseThrow(() -> new RuntimeException("Table not found with ID: " + order.getTable().getId()));
        List<Dish> verifiedDishes = new ArrayList<>();

        for (Dish dish : order.getDishes()) {
            Dish verifiedDish = dishRepo.findById(dish.getId())
                    .orElseThrow(() -> new RuntimeException("Dish not found with ID: " + dish.getId()));

            verifiedDishes.add(verifiedDish);
        }
        order.setTable(table);
        order.setDishes(verifiedDishes);
        return orderRepo.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    public List<Order> getOrdersByTableId(long tableId) {
        return orderRepo.findByTableId(tableId); // You might need to create this method in your repository
    }

    public Order getOrderById(long id) {
        Optional<Order> orderOptional = orderRepo.findById(id);
        return orderOptional.orElse(null);
    }

    public Order updateOrder(long id, Order order) {

        Order existingOrder = orderRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));

        existingOrder.setDishes(order.getDishes());
        existingOrder.setTable(order.getTable());

        return orderRepo.save(existingOrder);
    }

    public void deleteOrder(long id) throws Exception {
        if (!orderRepo.existsById(id)) {
            throw new Exception("Order not found with ID: " + id);
        }
        orderRepo.deleteById(id);
    }

    public void deleteOrdersByTableId(long tableId) {
        List<Order> orders = orderRepo.findByTableId(tableId);
        for (Order order : orders) {
            orderRepo.delete(order);
        }
    }

    public Order updateOrderByTableId(long tableId, Order order) {

        List<Order> existingOrders = orderRepo.findByTableId(tableId);

        if (!existingOrders.isEmpty()) {
            Order existingOrder = existingOrders.get(0);
            existingOrder.setDishes(order.getDishes());

            return orderRepo.save(existingOrder);
        } else {
            throw new RuntimeException("Order not found for table ID: " + tableId);
        }
    }
}