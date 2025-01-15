package com.jiinfotech.restomanager.controller;

import com.jiinfotech.restomanager.db.order.Order;
import com.jiinfotech.restomanager.db.order.OrderService;
import com.jiinfotech.restomanager.utils.Response;
import com.jiinfotech.restomanager.utils.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(Routes.orderCreate)
    protected ResponseEntity<Response<Order>> addOrder(@RequestBody Order order) {
        try {
            Order createdOrder = orderService.addOrder(order);
            Response<Order> response = new Response<>();
            response.setMessage("Order added successfully.");
            response.setStatus(HttpStatus.CREATED.value());
            response.setData(createdOrder);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            Response<Order> response = new Response<>();
            response.setMessage("Error adding order: " + e.getMessage());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(Routes.getAllOrders)
    protected ResponseEntity<Response<List<Order>>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        Response<List<Order>> response = new Response<>();
        response.setMessage("Orders retrieved successfully.");
        response.setStatus(HttpStatus.OK.value());
        response.setData(orders);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(Routes.getOrdersByTableID)
    protected ResponseEntity<Response<List<Order>>> getOrdersByTableId(@PathVariable long id) {
        List<Order> orders = orderService.getOrdersByTableId(id);
        Response<List<Order>> response = new Response<>();

        if (!orders.isEmpty()) {
            response.setMessage("Orders retrieved successfully.");
            response.setStatus(HttpStatus.OK.value());
            response.setData(orders);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("No orders found for this table.");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(Routes.getOrderByID)
    protected ResponseEntity<Response<Order>> getOrderById(@PathVariable long id) {
        Order order = orderService.getOrderById(id);
        Response<Order> response = new Response<>();

        if (order != null) {
            response.setMessage("Order retrieved successfully.");
            response.setStatus(HttpStatus.OK.value());
            response.setData(order);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Order not found.");
            response.setStatus(HttpStatus.NOT_FOUND.value());
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(Routes.updateOrderByTableID)
    protected ResponseEntity<Response<Order>> updateOrderByTableId(@PathVariable long id, @RequestBody Order order) {
        Response<Order> response = new Response<>();

        try {
            Order updatedOrder = orderService.updateOrderByTableId(id, order);
            response.setMessage("Order updated successfully.");
            response.setStatus(HttpStatus.OK.value());
            response.setData(updatedOrder);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("An error occurred: " + e.getMessage());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(Routes.orderDelete)
    protected ResponseEntity<Response<String>> deleteOrder(@PathVariable long id) throws Exception {
        orderService.deleteOrder(id);
        Response<String> response = new Response<>();
        response.setMessage("Order deleted successfully.");
        response.setStatus(HttpStatus.OK.value());
        response.setData("Order with ID " + id + " has been deleted.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(Routes.clearOrdersByTableID)
    public ResponseEntity<String> clearOrdersByTableId(@PathVariable long id) {
        try {
            orderService.deleteOrdersByTableId(id);
            return ResponseEntity.ok("All orders cleared for table " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while clearing orders.");
        }
    }
}