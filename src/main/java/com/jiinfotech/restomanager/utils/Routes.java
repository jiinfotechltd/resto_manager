package com.jiinfotech.restomanager.utils;

import org.springframework.stereotype.Component;

@Component
public class Routes {

    public static final String dashboard = "/dashboard";
    public static final String login = "/login";
    public static final String signup = "/signup";
    public static final String error = "/error";

    public static final String dish = "/dish";
    public static final String dishCreate = "/dish-create";
    public static final String dishUpdate = dish + "/update";
    public static final String dishDelete = dish + "/delete";
    public static final String getAllDishes = "/get-all-dishes";
    public static final String getDishById = dish + "/{id}";

    public static final String table = "/table";
    public static final String tableCreate = "/table-create";
    public static final String tableUpdate = table + "/update/{id}";
    public static final String tableDelete = table + "/delete/{id}";
    public static final String getTableById = table + "/{id}";
    public static final String getAllTable = "/get-all-tables";

    public static final String order = "/orders";
    public static final String orderCreate = "/order-create";
    public static final String updateOrderByTableID = order + "/update/table-id/{id}";
    public static final String orderDelete = order + "/delete/{id}";
    public static final String getOrderByID = order + "/{id}";
    public static final String getOrdersByTableID = order + "/table-id/{id}";
    public static final String getAllOrders = order + "/get-all-orders";
    public static final String clearOrdersByTableID = order + "/clear/table-id/{id}";

    public static final String getTempOrderTable = "/get-temp-order-table";
    public static final String postPlaceOrder = "/post-place-order";
    public static final String generateBill = "/generate-bill";

    public static final String bills = "/bills";
    public static final String createBill = "/create-bill";
    public static final String getBillById = bills + "/bill/{id}";
    public static final String billUpdate = bills + "/bill-update/{id}";
    public static final String billDelete = bills + "/bill-delete/{id}";
}
