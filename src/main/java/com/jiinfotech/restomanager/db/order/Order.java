package com.jiinfotech.restomanager.db.order;


import com.jiinfotech.restomanager.db.dish.Dish;
import com.jiinfotech.restomanager.db.table.RestaurantTable;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "table_id", nullable = false)
    private Long tableId;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }


    public Order() {
    }

    public Order(Long tableId, double totalAmount, Timestamp createdAt, Boolean isPaid) {
        this.tableId = tableId;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.isPaid = isPaid;
    }

}