package com.jiinfotech.restomanager.db.order;


import com.jiinfotech.restomanager.db.dish.Dish;
import com.jiinfotech.restomanager.db.table.RestaurantTable;
import jakarta.persistence.*;
import java.util.List;
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER) // Eager fetch for table
    @JoinColumn(name = "table_id", nullable = false)
    private RestaurantTable table;

    @ManyToMany(fetch = FetchType.EAGER) // Eager fetch for dishes
    @JoinTable(
            name = "order_dishes",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    private List<Dish> dishes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public RestaurantTable getTable() {
        return table;
    }

    public void setTable(RestaurantTable table) {
        this.table = table;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

}