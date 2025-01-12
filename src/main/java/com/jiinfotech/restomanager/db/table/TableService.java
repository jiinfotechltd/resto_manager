package com.jiinfotech.restomanager.db.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TableService {

    @Autowired
    private RestaurantTableRepo tableRepo;

    public RestaurantTable addTable(RestaurantTable restaurantTable) {
        return tableRepo.save(restaurantTable);
    }

    public List<RestaurantTable> getAllTables() {
        return tableRepo.findAll();
    }

    public RestaurantTable getTableById(long id) {
        Optional<RestaurantTable> tableOptional = tableRepo.findById(id);
        return tableOptional.orElse(null);
    }

    public RestaurantTable updateTable(long id, RestaurantTable restaurantTable) throws Exception {
        if (!tableRepo.existsById(id)) {
            throw new Exception("Table not found with ID: " + id);
        }
        restaurantTable.setId(id);
        return tableRepo.save(restaurantTable);
    }

    public void deleteTable(long id) throws Exception {
        if (!tableRepo.existsById(id)) {
            throw new Exception("Table not found with ID: " + id);
        }
        tableRepo.deleteById(id);
    }
}

