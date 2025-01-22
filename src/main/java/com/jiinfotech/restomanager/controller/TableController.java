package com.jiinfotech.restomanager.controller;

import com.jiinfotech.restomanager.db.table.RestaurantTable;
import com.jiinfotech.restomanager.db.table.TableService;
import com.jiinfotech.restomanager.utils.BaseServices;
import com.jiinfotech.restomanager.utils.Response;
import com.jiinfotech.restomanager.utils.Routes;
import com.jiinfotech.restomanager.utils.Templates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Comparator;
import java.util.List;

@Controller
public class TableController extends BaseServices {

    @Autowired
    private TableService tableService;

    @PostMapping(Routes.tableCreate)
    public String addTable(@ModelAttribute RestaurantTable restaurantTable, Model model) {
        tableService.addTable(restaurantTable);
        return redirect(Routes.table);
    }

    @GetMapping(Routes.table)
    public String showTables(Model model) {
        List<RestaurantTable> tables = tableService.getAllTabesForDisplay();
        tables.sort(Comparator.comparingInt(RestaurantTable::getTableNumber));
        model.addAttribute("tables", tables);
        model.addAttribute("table", new RestaurantTable());
        return Templates.table;
    }

    @GetMapping(value = Routes.getTableById)
    protected ResponseEntity<Response<RestaurantTable>> getTableById(@PathVariable long id) {
        Response<RestaurantTable> response = new Response<>();
        RestaurantTable table = tableService.getTableById(id);

        if (table != null) {
            response.setMessage("Table retrieved successfully");
            response.setStatus(HttpStatus.OK.value());
            response.setData(table);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("Table not found with ID: " + id);
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = Routes.tableUpdate)
    public String updateTable(
            @ModelAttribute RestaurantTable restaurantTable,
            @RequestParam(value = "vacant", required = false) Boolean isVacant) {
        restaurantTable.setIsVacant(isVacant); // Sets the value
        tableService.updateTable(restaurantTable);
        return redirect(Routes.table);
    }

    @PostMapping(value = Routes.tableDelete)
    public String deleteTable(@RequestParam long id) {
            tableService.deleteTable(id);
            return redirect(Routes.table);
    }
}

