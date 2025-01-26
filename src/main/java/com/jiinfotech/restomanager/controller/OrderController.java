package com.jiinfotech.restomanager.controller;

import com.jiinfotech.restomanager.db.dish.Dish;
import com.jiinfotech.restomanager.db.dish.DishService;
import com.jiinfotech.restomanager.db.order.Order;
import com.jiinfotech.restomanager.db.order.OrderService;
import com.jiinfotech.restomanager.db.order_dishes.oder_dishes.OrderDishesServices;
import com.jiinfotech.restomanager.db.table.TableService;
import com.jiinfotech.restomanager.forms.TableAndOrderForm;
import com.jiinfotech.restomanager.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        sessionStorage.setSessionStorage(tableId, dishId, quan);
        AjaxResponse ajaxResponse = new AjaxResponse();
        HashMap<String, Object> params = new HashMap<>();
        params.put("tableId", tableId);
        ajaxResponse.msg = "new dish created";
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
}