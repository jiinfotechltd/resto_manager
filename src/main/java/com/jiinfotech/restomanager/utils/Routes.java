package com.jiinfotech.restomanager.utils;

public class Routes {

    public static final String dashboard = "/";
    public static final String login = "/login";
    public static final String signup = "/signup";
    public static final String error = "/error";

    public static final String dish = "/dish";
    public static final String dishCreate = "/dish-create";
    public static final String dishUpdate = "/dish-update";
    public static final String dishDelete = "/dish-delete";
    public static final String getAllDishes = "/get-all-dishes";
    public static final String getDishById = dish + "/{id}";

    public static final String table = "/table";
    public static final String tableCreate = "/table-create";
    public static final String tableUpdate = table + "/update/{id}";
    public static final String tableDelete = table + "/delete/{id}";
    public static final String getTableById = table + "/{id}";
    public static final String getAllTable =  "/get-all-tables";



}
