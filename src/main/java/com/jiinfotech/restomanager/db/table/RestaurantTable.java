package com.jiinfotech.restomanager.db.table;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

@Entity
@Table(name = "restaurant_table")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "table_number", nullable = false, unique = true)
    private int tableNumber;

    @Column(name = "is_vacant", nullable = false)
    private Boolean isVacant;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    // Optional: Bidirectional relationship with Order entity if needed
//    @OneToMany(mappedBy = "table")
//    private List<Order> orders;

    public RestaurantTable() {}

    public RestaurantTable(int tableNumber, boolean isVacant, int capacity) {
        this.tableNumber = tableNumber;
        this.isVacant = isVacant;
        this.capacity = capacity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Boolean getIsVacant() {
        return isVacant;
    }

    public void setIsVacant(Boolean status) {
        this.isVacant = status;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "RestaurantTable{" + "id=" + id + ", tableNumber=" + tableNumber + ", status='" + isVacant + '\'' + ", capacity=" + capacity + '}';
    }
}