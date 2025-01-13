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
    public RestaurantTable saveTable(long id, RestaurantTable table) {
        return tableRepo.save(table);
    }

    public void updateTable(RestaurantTable restaurantTable) {
        Optional<RestaurantTable> existingTable = tableRepo.findById(restaurantTable.getId());
        if (existingTable.isPresent()) {
            RestaurantTable table = existingTable.get();
            table.setCapacity(restaurantTable.getCapacity());
            table.setIsVacant(restaurantTable.getIsVacant());
            tableRepo.save(table);
        }
    }

    public void deleteTable(long id){
       Optional<RestaurantTable> maybeTable= tableRepo.findById(id);
       if(maybeTable.isPresent()){
           tableRepo.delete(maybeTable.get());
       }
    }
}

