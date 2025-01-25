package com.jiinfotech.restomanager.controller;

import com.jiinfotech.restomanager.db.dish.Dish;
import com.jiinfotech.restomanager.db.dish.DishService;
import com.jiinfotech.restomanager.db.order.Order;
import com.jiinfotech.restomanager.db.order.OrderService;
import com.jiinfotech.restomanager.db.order_dishes.oder_dishes.OrderDishesServices;
import com.jiinfotech.restomanager.db.table.TableService;
import com.jiinfotech.restomanager.forms.TableAndOrderForm;
import com.jiinfotech.restomanager.utils.*;
import org.hibernate.mapping.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController extends BaseServices {

    @Autowired
    private SessionStorage sessionStorage;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDishesServices orderDishesServices;
    @Autowired
    private TableService tableService;
    @Autowired
    private DishService dishService;
    @Autowired
    private GoogleChatNotificationService googleChatNotificationService;


    @PostMapping(Routes.orderCreate)
    @ResponseBody
    public String postOrderCreate(@RequestParam("tableId") Long tableId, @RequestParam("dishId") Long dishId, @RequestParam("quan") int quan) throws Exception {
//        orderDishesServices.createNewOrder(tableId, dishId, quan);
        sessionStorage.setSessionStorage(tableId, dishId, quan);
        AjaxResponse ajaxResponse = new AjaxResponse();
        HashMap<String, Object> params = new HashMap<>();
        params.put("tableId", tableId);
        ajaxResponse.msg = "new dish creared";
        ajaxResponse.msgType = "success";
        ajaxResponse.parameters = toJson(params);
        return toJson(ajaxResponse);
    }

    @GetMapping(Routes.getTempOrderTable)
    public String getTempOrderTable(Model model, @RequestParam long tableId) {

        List<TableAndOrderForm> allTables = tableService.getAllTables();
        List<Dish> allDishes = dishService.getAllDishes();

        List<Order> allOrders = orderService.getAllOrderForTable();
        SessionStorage getSession = sessionStorage.getSession();

        Optional<TableAndOrderForm> tableAndOrderForm = allTables.stream()
                .filter(t -> t.getRestaurantTable().getId() == tableId)
                .findFirst();

        model.addAttribute("allTables", allTables);
        model.addAttribute("table", tableAndOrderForm.get());
        model.addAttribute("allDishes", allDishes);
        model.addAttribute("allOrders", allOrders);
        model.addAttribute("tableAndOrderForm", new TableAndOrderForm());
        return "fragments/main-page-fragments ::" + Fragments.getTempOrderTable;
    }

    @PostMapping(Routes.generateBill)
    public String generateBill(@RequestParam Long tableId, @RequestParam Long orderId) {

        ByteArrayInputStream pdfStream = orderDishesServices.genrateBill(tableId, orderId);
        try (FileOutputStream outputStream = new FileOutputStream(tableId+orderId.toString()+"bill.pdf")) {
            byte[] buffer = pdfStream.readAllBytes();
            outputStream.write(buffer);
        } catch (IOException e) {
            googleChatNotificationService.sendNotification("unable to generate pdf for bill");
            e.printStackTrace();
            return "error"; // Redirect to an error page if needed
        }
        return redirect(Routes.dashboard);
    }

    @PostMapping(Routes.postPlaceOrder)
    public String postPlaceOrder(@RequestParam long tableId) {
        List<TableAndOrderForm> allTables = tableService.getAllTables();
        Optional<TableAndOrderForm> tableAndOrderForm = allTables.stream()
                .filter(t -> t.getRestaurantTable().getId() == tableId)
                .findFirst();


        orderDishesServices.createNewOrder(tableAndOrderForm);

        System.out.println(tableAndOrderForm);


        return redirect(Routes.dashboard);
    }
//    @PostMapping(Routes.orderCreate)
//    protected ResponseEntity<Response<Order>> addOrder(@RequestBody Order order) {
//        try {
//            Order createdOrder = orderService.addOrder(order);
//            Response<Order> response = new Response<>();
//            response.setMessage("Order added successfully.");
//            response.setStatus(HttpStatus.CREATED.value());
//            response.setData(createdOrder);
//            return new ResponseEntity<>(response, HttpStatus.CREATED);
//        } catch (Exception e) {
//            Response<Order> response = new Response<>();
//            response.setMessage("Error adding order: " + e.getMessage());
//            response.setStatus(HttpStatus.BAD_REQUEST.value());
//            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @GetMapping(Routes.getAllOrders)
//    protected ResponseEntity<Response<List<Order>>> getAllOrders() {
//        List<Order> orders = orderService.getAllOrders();
//        Response<List<Order>> response = new Response<>();
//        response.setMessage("Orders retrieved successfully.");
//        response.setStatus(HttpStatus.OK.value());
//        response.setData(orders);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping(Routes.getOrdersByTableID)
//    protected ResponseEntity<Response<List<Order>>> getOrdersByTableId(@PathVariable long id) {
//        List<Order> orders = orderService.getOrdersByTableId(id);
//        Response<List<Order>> response = new Response<>();
//
//        if (!orders.isEmpty()) {
//            response.setMessage("Orders retrieved successfully.");
//            response.setStatus(HttpStatus.OK.value());
//            response.setData(orders);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } else {
//            response.setMessage("No orders found for this table.");
//            response.setStatus(HttpStatus.NOT_FOUND.value());
//            response.setData(null);
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @GetMapping(Routes.getOrderByID)
//    protected ResponseEntity<Response<Order>> getOrderById(@PathVariable long id) {
//        Order order = orderService.getOrderById(id);
//        Response<Order> response = new Response<>();
//
//        if (order != null) {
//            response.setMessage("Order retrieved successfully.");
//            response.setStatus(HttpStatus.OK.value());
//            response.setData(order);
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } else {
//            response.setMessage("Order not found.");
//            response.setStatus(HttpStatus.NOT_FOUND.value());
//            response.setData(null);
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PutMapping(Routes.updateOrderByTableID)
//    protected ResponseEntity<Response<Order>> updateOrderByTableId(@PathVariable long id, @RequestBody Order order) {
//        Response<Order> response = new Response<>();
//
//        try {
//            Order updatedOrder = orderService.updateOrderByTableId(id, order);
//            response.setMessage("Order updated successfully.");
//            response.setStatus(HttpStatus.OK.value());
//            response.setData(updatedOrder);
//
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (Exception e) {
//            response.setMessage("An error occurred: " + e.getMessage());
//            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @DeleteMapping(Routes.orderDelete)
//    protected ResponseEntity<Response<String>> deleteOrder(@PathVariable long id) throws Exception {
//        orderService.deleteOrder(id);
//        Response<String> response = new Response<>();
//        response.setMessage("Order deleted successfully.");
//        response.setStatus(HttpStatus.OK.value());
//        response.setData("Order with ID " + id + " has been deleted.");
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @DeleteMapping(Routes.clearOrdersByTableID)
//    public ResponseEntity<String> clearOrdersByTableId(@PathVariable long id) {
//        try {
//            orderService.deleteOrdersByTableId(id);
//            return ResponseEntity.ok("All orders cleared for table " + id);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while clearing orders.");
//        }
//    }
}