package com.jiinfotech.restomanager.forms;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiinfotech.restomanager.db.dish.Dish;
import com.jiinfotech.restomanager.db.order.Order;
import com.jiinfotech.restomanager.db.order_dishes.oder_dishes.OrderDishes;
import com.jiinfotech.restomanager.db.table.RestaurantTable;
import org.springframework.stereotype.Component;

import java.beans.BeanProperty;
import java.util.List;

@Component
public class TableAndOrderForm {

    @JsonProperty
    private RestaurantTable restaurantTable;

    @JsonProperty
    private Order order;

    @JsonProperty
    private List<Order> ordersList;

    @JsonProperty
    private double totalAmount;

    @JsonProperty
    private List<OrderDishes> allDishes;

    public TableAndOrderForm() {

    }

    public List<Order> getOrdersList(){
        return this.ordersList;
    }

    public void setOrdersList( List<Order> ordersList){
        this.ordersList = ordersList;
    }

    public RestaurantTable getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTable = restaurantTable;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderDishes> getAllDishes() {
        return allDishes;
    }

    public void setAllDishes(List<OrderDishes> allDishes) {
        this.allDishes = allDishes;
    }
}
