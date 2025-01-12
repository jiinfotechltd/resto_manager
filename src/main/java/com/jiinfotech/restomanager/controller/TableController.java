package com.jiinfotech.restomanager.controller;

import com.jiinfotech.restomanager.db.table.RestaurantTable;
import com.jiinfotech.restomanager.db.table.TableService;
import com.jiinfotech.restomanager.utils.Response;
import com.jiinfotech.restomanager.utils.Routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class TableController {

    @Autowired
    private TableService tableService;

    @PostMapping(value = Routes.tableCreate)
    protected ResponseEntity<Response<RestaurantTable>> addTable(@RequestBody RestaurantTable restaurantTable) {
        Response<RestaurantTable> response = new Response<>();

        try {
            RestaurantTable tableToAdd = tableService.addTable(restaurantTable);
            response.setMessage("New Table is Added");
            response.setStatus(HttpStatus.CREATED.value());
            response.setData(tableToAdd);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.setMessage("Failed to add new table: " + e.getMessage());
            response.setStatus(HttpStatus.CONFLICT.value());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }
    }

    @GetMapping(value = Routes.getAllTable)
    protected ResponseEntity<Response<List<RestaurantTable>>> getAllTables() {
        Response<List<RestaurantTable>> response = new Response<>();
        List<RestaurantTable> tables = tableService.getAllTables();

        response.setMessage("List of all tables retrieved");
        response.setStatus(HttpStatus.OK.value());
        response.setData(tables);

        return new ResponseEntity<>(response, HttpStatus.OK);
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

    @PutMapping(value = Routes.tableUpdate)
    protected ResponseEntity<Response<RestaurantTable>> updateTable(@PathVariable long id, @RequestBody RestaurantTable restaurantTable) {
        Response<RestaurantTable> response = new Response<>();

        try {
            RestaurantTable updatedTable = tableService.updateTable(id, restaurantTable);
            response.setMessage("Table updated successfully");
            response.setStatus(HttpStatus.OK.value());
            response.setData(updatedTable);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Failed to update table: " + e.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = Routes.tableDelete)
    protected ResponseEntity<Response<String>> deleteTable(@PathVariable long id) {
        Response<String> response = new Response<>();

        try {
            tableService.deleteTable(id);
            response.setMessage("Table deleted successfully");
            response.setStatus(HttpStatus.OK.value());
            response.setData("Deleted table with ID: " + id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setMessage("Failed to delete table: " + e.getMessage());
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}

