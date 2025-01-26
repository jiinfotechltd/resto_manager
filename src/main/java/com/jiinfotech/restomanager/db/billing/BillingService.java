package com.jiinfotech.restomanager.db.billing;

import com.jiinfotech.restomanager.db.order.Order;
import com.jiinfotech.restomanager.db.order.OrderRepo;
import com.jiinfotech.restomanager.db.order_dishes.oder_dishes.OrderDishes;
import com.jiinfotech.restomanager.db.order_dishes.oder_dishes.OrderDishesRepo;
import com.jiinfotech.restomanager.db.table.RestaurantTableRepo;
import com.jiinfotech.restomanager.utils.BillGenerateServices;
import com.jiinfotech.restomanager.utils.GoogleChatNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;

@Service
public class BillingService {

    @Autowired
    private BillingRepo billingRepo;
    @Autowired
    private RestaurantTableRepo restaurantTableRepo;
    @Autowired
    private GoogleChatNotificationService googleChatNotificationService;
    @Autowired
    private BillGenerateServices billGenerateServices;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderDishesRepo orderDishesRepo;

    public Billing createBilling(Billing billing) {
        return billingRepo.save(billing);
    }

    public List<Billing> getAllBillings() {
        return billingRepo.findAll();
    }

    public Billing getBillingById(long id) {
        return billingRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Billing entry not found with ID: " + id));
    }

    public Billing updateBilling(long id, Billing billing) {
        if (!billingRepo.existsById(id)) {
            throw new RuntimeException("Billing entry not found with ID: " + id);
        }
        billing.setId(id);
        return billingRepo.save(billing);
    }

    public void deleteBilling(long id) {
        if (!billingRepo.existsById(id)) {
            throw new RuntimeException("Billing entry not found with ID: " + id);
        }
        billingRepo.deleteById(id);
    }

    public ByteArrayInputStream generateBill(Long tableId, Long orderId) {
        Optional<Order> mayBeOrder = orderRepo.findById(orderId);

        if (mayBeOrder.isPresent()) {
            List<OrderDishes> allDishes = orderDishesRepo.findAllByOrderId(orderId);
            String[] allDishesNames = allDishes.stream().map(OrderDishes::getDishName).toArray(String[]::new);
            Double[] allDishesTotalAmount = allDishes.stream().map(OrderDishes::getDishTotalAmount).toArray(Double[]::new);
            mayBeOrder.get().setIsPaid(true);
            orderRepo.save(mayBeOrder.get());
            return billGenerateServices.generateAttractiveBill("Sharyat Hotel", "Kolhapur","9853212123", "www.jiinfoteh.com" , allDishesNames, allDishesTotalAmount, mayBeOrder.get().getTotalAmount());
        }
        googleChatNotificationService.sendNotification("With order ID: "+orderId +" Order not found");
        return null;
    }
}
