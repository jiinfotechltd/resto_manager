package com.jiinfotech.restomanager.db.order_dishes.oder_dishes;

import com.jiinfotech.restomanager.db.dish.Dish;
import com.jiinfotech.restomanager.db.dish.DishRepo;
import com.jiinfotech.restomanager.db.order.Order;
import com.jiinfotech.restomanager.db.order.OrderRepo;
import com.jiinfotech.restomanager.db.table.RestaurantTableRepo;
import com.jiinfotech.restomanager.forms.TableAndOrderForm;
import com.jiinfotech.restomanager.utils.BaseServices;
import com.jiinfotech.restomanager.utils.BillGenerateServices;
import com.jiinfotech.restomanager.utils.GoogleChatNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDishesServices extends BaseServices {

    @Autowired
    private DishRepo dishRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderDishesRepo orderDishesRepo;
    @Autowired
    private RestaurantTableRepo restaurantTableRepo;
    @Autowired
    private GoogleChatNotificationService googleChatNotificationService;
    @Autowired
    private BillGenerateServices billGenerateServices;

    public void createNewOrder(Optional<TableAndOrderForm> tableAndOrderForm) {
        if (tableAndOrderForm.isPresent() && tableAndOrderForm.get().getTotalAmount() > 0) {
            Order order = new Order();
            order.setTableId(tableAndOrderForm.get().getRestaurantTable().getId());
            order.setCreatedAt(getCurrentTimestamp());
            order.setTotalAmount(tableAndOrderForm.get().getTotalAmount());
            order.setIsPaid(false);
            Order savedOrder = orderRepo.save(order);
            if (tableAndOrderForm.get().getAllDishes() != null) {
                tableAndOrderForm.get().getAllDishes().forEach(dish -> {
                    Optional<Dish> mayBeDish = dishRepo.findById(dish.getDishId());
                    if (mayBeDish.isPresent()) {
                        OrderDishes orderDishes = new OrderDishes();
                        orderDishes.setOrderId(savedOrder.getId());
                        orderDishes.setDishQuantity(dish.getDishQuantity());
                        orderDishes.setDishPrice(dish.getDishPrice());
                        orderDishes.setDishId(dish.getDishId());
                        orderDishes.setDishName(dish.getDishName());
                        orderDishes.setDishTotalAmount(dish.getDishTotalAmount());
                        orderDishesRepo.save(orderDishes);

                    } else {
                        googleChatNotificationService.sendNotification("Unable to get the dish with Id" + dish.getId());
                    }
                });
            }
            googleChatNotificationService.sendNotification("Order is placed " + tableAndOrderForm.get().getTotalAmount() + " Rs for table + " + tableAndOrderForm.get().getRestaurantTable().getId());
        } else {
            googleChatNotificationService.sendNotification("error when order placing in table please check");
        }


//        orderDishes.setDishId(dishId);
//        orderDishes.setDishQuantity(dishQun);
//        Optional<RestaurantTable> restaurantTable = restaurantTableRepo.findById(tableId);
//        if (restaurantTable.isPresent()) {
//            restaurantTable.get().setIsVacant(false);
//            restaurantTableRepo.save(restaurantTable.get());
//        }
//
//        Order order = new Order();
//        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
//        order.setCreatedAt(currentTime);
//        order.setIsPaid(false);
//        order.setTableId(tableId);
//        Optional<Dish> mayBeDish = dishRepo.findById(dishId);
//        double orderamount = 0.0;
//        if (mayBeDish.isPresent()) {
//            orderamount += mayBeDish.get().getPrice();
//        }
//        order.setTotalAmount(orderamount);
//
//        Order saved = orderRepo.save(order);
//        orderDishes.setOrderId(saved.getId());
//        orderDishesRepo.save(orderDishes);
    }


    public void updateOrder(Long tableId, Long[] dishIds) {

    }

    public ByteArrayInputStream genrateBill(Long tableId, Long orderId) {
        Optional<Order> mayBeOrder = orderRepo.findByIdAndIsPaid(orderId, false);

        if (mayBeOrder.isPresent()) {
            List<OrderDishes> allDishes = orderDishesRepo.findAllByOrderId(orderId);
            String[] allDishesNames = allDishes.stream().map(OrderDishes::getDishName).toArray(String[]::new);
            Double[] allDishesTotalAmount = allDishes.stream().map(OrderDishes::getDishTotalAmount).toArray(Double[]::new);
            mayBeOrder.get().setIsPaid(true);
            orderRepo.save(mayBeOrder.get());
            return billGenerateServices.generateAttractiveBill("Sharyat Hotel", "Kolhapur","9853212123", "www.jiinfoteh.com" , allDishesNames, allDishesTotalAmount, mayBeOrder.get().getTotalAmount());
        }
        googleChatNotificationService.sendNotification("unable to generate Bill ");
        return null;
    }
}
