package com.jiinfotech.restomanager.db.table;

import com.jiinfotech.restomanager.db.order.Order;
import com.jiinfotech.restomanager.forms.TableAndOrderForm;
import com.jiinfotech.restomanager.utils.GoogleChatNotificationService;
import com.jiinfotech.restomanager.utils.SessionStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TableService {

    @Autowired
    private RestaurantTableRepo tableRepo;
    @Autowired
    private GoogleChatNotificationService googleChatNotificationService;
    @Autowired
    private SessionStorage sessionStorage;

    public void addTable(RestaurantTable restaurantTable) {
        Optional<RestaurantTable> mayBeTable = tableRepo.findByTableNumber(restaurantTable.getTableNumber());
        if (mayBeTable.isPresent()) {
            googleChatNotificationService.sendNotification("Error when creating table with same number" + mayBeTable.get().getTableNumber());
        } else {
            tableRepo.save(restaurantTable);
        }
    }

    public List<TableAndOrderForm> getAllTables() {
        SessionStorage getSessionStorage = sessionStorage.getSession();
        if (sessionStorage.getTableAndThereOrder() != null && !sessionStorage.getTableAndThereOrder().isEmpty()) {
            List<TableAndOrderForm> tableAndOrderForms = tableRepo.findAll().stream().map(table -> {
                TableAndOrderForm tableAndOrdersDetails = new TableAndOrderForm();
                tableAndOrdersDetails.setRestaurantTable(table); // Set the restaurant table
                double totalAmount = 0.0;
                if (getSessionStorage.getTableAndThereOrder().containsKey(table)) {
                    totalAmount = getSessionStorage.getTableAndThereOrder().get(table).keySet().iterator().next().getTotalAmount();
                    Order order = getSessionStorage.getTableAndThereOrder().get(table).keySet().iterator().next();
                    tableAndOrdersDetails.setAllDishes(getSessionStorage.getTableAndThereOrder().get(table).get(order));
                    tableAndOrdersDetails.setTotalAmount(totalAmount);
                }
                return tableAndOrdersDetails;
            }).toList();
            return tableAndOrderForms;
        } else {
            List<TableAndOrderForm> tableAndOrderForms = tableRepo.findAll().stream().map(table -> {
                TableAndOrderForm tableAndOrderForm = new TableAndOrderForm();
                tableAndOrderForm.setRestaurantTable(table);
                return tableAndOrderForm;
            }).toList();
            return tableAndOrderForms;
        }
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

    public void deleteTable(long id) {
        Optional<RestaurantTable> maybeTable = tableRepo.findById(id);
        if (maybeTable.isPresent()) {
            tableRepo.delete(maybeTable.get());
        }
    }

    public List<RestaurantTable> getAllTablesForDisplay() {
        return tableRepo.findAll();
    }
}

