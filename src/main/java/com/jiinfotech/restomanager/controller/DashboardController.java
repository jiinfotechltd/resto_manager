package com.jiinfotech.restomanager.controller;

import com.jiinfotech.restomanager.db.billing.Billing;
import com.jiinfotech.restomanager.db.dish.Dish;
import com.jiinfotech.restomanager.db.dish.DishService;
import com.jiinfotech.restomanager.db.order.Order;
import com.jiinfotech.restomanager.db.order.OrderService;
import com.jiinfotech.restomanager.db.table.TableService;
import com.jiinfotech.restomanager.forms.TableAndOrderForm;
import com.jiinfotech.restomanager.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class DashboardController extends BaseServices {

    @Autowired
    private OrderService orderService;
    @Autowired
    private TableService tableService;
    @Autowired
    private DishService dishService;
    @Autowired
    private SessionStorage sessionStorage;

    @GetMapping(Routes.dashboard)
    public String showDashboard(Model model) {
        List<TableAndOrderForm> allTables = tableService.getAllTables();
        List<Dish> allDishes = dishService.getAllDishes();
        List<Order> allOrders = orderService.getAllOrderForTable();
        SessionStorage getSession = sessionStorage.getSession();

        model.addAttribute("allTables", allTables);
        model.addAttribute("allDishes", allDishes);
        model.addAttribute("allOrders", allOrders);
        model.addAttribute("billing", new Billing());

        return Templates.index;
    }

    @GetMapping(Routes.login)
    public String getLogin(Model model) {
        return Templates.login;
    }

    @PostMapping(Routes.login)
    public String postLogin(Model model) {
        return redirect(Routes.dish);
    }
}