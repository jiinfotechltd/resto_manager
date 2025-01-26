package com.jiinfotech.restomanager.db.order_dishes.oder_dishes;

import jakarta.persistence.*;

@Entity
@Table(name = "order_dishes")
public class OrderDishes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "dish_id", nullable = false)
    private Long dishId;

    @Column(name = "dish_quant", nullable = false)
    private Integer dishQuantity;

    @Column(name = "dish_name", nullable = false)
    private String dishName;

    @Column(name = "dish_price", nullable = false)
    private double dishPrice ;

    @Column(name = "dish_total_amount", nullable = false)
    private double dishTotalAmount ;

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public double getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(double dishPrice) {
        this.dishPrice = dishPrice;
    }

    public double getDishTotalAmount() {
        return dishTotalAmount;
    }

    public void setDishTotalAmount(double dishTotalAmount) {
        this.dishTotalAmount = dishTotalAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Integer getDishQuantity() {
        return dishQuantity;
    }

    public void setDishQuantity(Integer dishQuantity) {
        this.dishQuantity = dishQuantity;
    }

    public OrderDishes() {
    }

    public OrderDishes(Long orderId, Long dishId, Integer dishQuantity) {
        this.orderId = orderId;
        this.dishId = dishId;
        this.dishQuantity = dishQuantity;
    }
}